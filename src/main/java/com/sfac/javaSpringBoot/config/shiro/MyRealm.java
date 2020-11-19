package com.sfac.javaSpringBoot.config.shiro;

import com.sfac.javaSpringBoot.modules.account.entity.Resource;
import com.sfac.javaSpringBoot.modules.account.entity.Role;
import com.sfac.javaSpringBoot.modules.account.entity.RoleResource;
import com.sfac.javaSpringBoot.modules.account.entity.User;
import com.sfac.javaSpringBoot.modules.account.service.ResourceService;
import com.sfac.javaSpringBoot.modules.account.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {

/*
    ============= day_12 ===========
    shiro
            身份验证过程
    用户登录 ---- 包装令牌（用户名、密码、记住我） ---- subject 调用 realm，包装身份验证器 ---- 身份验证器和令牌做比对（用户名和密码的比对）
    资源授权流程
    用户登录 ----- subject调用 checkroles（） ---- 跳转页面 ---- 当页面有 shiro 标签 或者方法上有 shiro 注解时候 ---- 调用 realm 里面的资源授权器 ---- 资源授权器和标签或注解上的内容做比对

*/



    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
//授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
         SimpleAuthorizationInfo simpleAuthorizationInfo=
                 new SimpleAuthorizationInfo();
         User user= (User) principalCollection.getPrimaryPrincipal();
         List<Role> roles=user.getRoles();
         if (roles!=null&&!roles.isEmpty()){
             roles.stream().forEach(item->{
                 simpleAuthorizationInfo.addRole(item.getRoleName());
                 List<Resource> resources=resourceService.getResourcesByRoleId(item.getRoleId());
                 if (resources!=null&&!resources.isEmpty()){
                     resources.stream().forEach(resource -> {
                         simpleAuthorizationInfo.addStringPermission(resource.getPermission());
                     });
                 }
             });
         }

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
     //认证开始
      String userName= (String) authenticationToken.getPrincipal();

      User user=userService.getUserByUserName(userName);

      if (user==null){
          throw  new UnknownAccountException("the account do not exist.");

      }
//principal在这儿相当于一个object对象，同时底层放入了seesion对象当中
// 因为下面的new中传入了用户带有userName属性，所以在认证那的时候就可以直接
// 引用，同样前端也可以直接获得
        return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
    }
}
