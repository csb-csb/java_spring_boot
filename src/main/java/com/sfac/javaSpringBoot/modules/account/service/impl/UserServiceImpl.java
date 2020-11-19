package com.sfac.javaSpringBoot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.config.ResourceConfigBean;
import com.sfac.javaSpringBoot.modules.account.dao.UserDao;
import com.sfac.javaSpringBoot.modules.account.dao.UserRoleDao;
import com.sfac.javaSpringBoot.modules.account.entity.Role;
import com.sfac.javaSpringBoot.modules.account.entity.User;
import com.sfac.javaSpringBoot.modules.account.service.UserService;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;



@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private ResourceConfigBean resourceConfigBean;

    @Override
    @Transactional
    public Result<User> inserUser(User user) {
        User user1=userDao.getUserByUserName(user.getUserName());
        if (user1!=null){
            return new Result<User>(Result.ResultStatus.FAILE.status,"user name is repeat");
        }else {
            user.setCreateDate(LocalDateTime.now());
            user.setPassword(MD5Util.getMD5(user.getPassword()));
            userDao.insertUser(user);

            userRoleDao.deleteUserRoleByUserId(user.getUserId());
            List<Role> roles=user.getRoles();
            if (roles!=null&&!roles.isEmpty()){
                roles.stream().forEach(item->{
                    userRoleDao.insertUserRole(user.getUserId(),item.getRoleId());
                });
            }

            return new Result<User>(Result.ResultStatus.SUCCESS.status,"insert SUCCESS",user);
        }


    }

    @Override
    public Result<User> login(User user) {

        Subject subject = SecurityUtils.getSubject();
       //令牌累装入前端页面装入用户的姓名和密码
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken(user.getUserName(),
                        MD5Util.getMD5(user.getPassword()));
        usernamePasswordToken.setRememberMe(user.getRememberMe());

        try {
            //调用login登录的方法，然后将令牌装入
            subject.login(usernamePasswordToken);
            subject.checkRoles();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<User>(Result.ResultStatus.FAILE.status,
                    "UserName or password is error.");
        }

        Session session = subject.getSession();
        session.setAttribute("user", (User)subject.getPrincipal());

        return new Result<User>(Result.ResultStatus.SUCCESS.status,
                "Login success.", user);

    }

    @Override
    public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<User>(
                Optional.ofNullable(userDao.getUsersBySearchVo(searchVo))
                        .orElse(Collections.emptyList()));
    }

    @Override
    @Transactional
    public Result<User> updateUser(User user) {
        User user1=userDao.getUserByUserName(user.getUserName());
        if (user1!=null&&user.getUserId()!=user1.getUserId()){
        return new Result<User>(Result.ResultStatus.FAILE.status,"user name is repeat") ;
        }
        userDao.updateUser(user);

        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        List<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            roles.stream().forEach(item -> {
                userRoleDao.insertUserRole(user.getUserId(), item.getRoleId());
            });
        }

        return new Result<User>(Result.ResultStatus.SUCCESS.status,"updateSuccess",user);
    }

    @Override
    @Transactional
    public Result<User> deleteUser(int userId) {
        userDao.deleteUser(userId);
        userRoleDao.deleteUserRoleByUserId(userId);
        return new Result<User>(Result.ResultStatus.SUCCESS.status,"delete success");
    }

    @Override
    public User getUserByUserId(int userId) {

        return  userDao.getUserByUserId(userId);
    }

    @Override
    public Result<String> uploadUserImg(MultipartFile file) {
       if (file.isEmpty()){
           return new Result<String>(Result.ResultStatus.FAILE.status,"Please select img");
       }
           String relativePath="";
           String destFilePath="";
        try {
           String osName=System.getProperty("os.name");
           if (osName.toLowerCase().startsWith("win")){
               destFilePath=resourceConfigBean.getLocationPathForWindows()+
                       file.getOriginalFilename();
       }else {
               destFilePath=resourceConfigBean.getLocationPathForLinux()+
                       file.getOriginalFilename();
           }
           relativePath=resourceConfigBean.getRelativePath()+
                   file.getOriginalFilename();
        File file1=new File(destFilePath);
        file.transferTo(file1);

        } catch (IOException e) {

            e.printStackTrace();
            return new Result<String>(Result.ResultStatus.FAILE.status,"upload file");
        }
        return new Result<String>(Result.ResultStatus.SUCCESS.status,"upload success",relativePath);
    }

    @Override
    @Transactional
    public Result<User> updateUserProfile(User user) {
        User user1=userDao.getUserByUserName(user.getUserName());
        if (user1!=null&&user1.getUserId()!=user.getUserId()){
            return new Result<User>(Result.ResultStatus.FAILE.status,"user name is repeat");
        }else {
            userDao.updateUser(user);
            return new Result<User>(Result.ResultStatus.SUCCESS.status,"edit success",user);
        }

    }

    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        Session session = subject.getSession();
        session.setAttribute("user", null);
    }
}
