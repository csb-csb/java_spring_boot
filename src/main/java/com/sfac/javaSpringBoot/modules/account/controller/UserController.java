package com.sfac.javaSpringBoot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.entity.User;
import com.sfac.javaSpringBoot.modules.account.service.UserService;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/*
* controlle与restController之间的区别，restController相当于controller与responbody
* 的组合体，controller内部有视图解析器，所以能识别返回的jsp文件，也需要modelmap的加持
* 返回json对象，就需要加resonsebody注解，
* 同样使用该注解，一般就和跳转页面无关。而，restController中没有视图解析器，
* 所以只能返回对象，返回的东西一般就绑定在该方法上，对应的前端一般也是发送的json对象，
* controller返回“”，即为空白页面
*
* */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;


    //127.0.0.1/api/login
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> login(@RequestBody User user){
        return userService.login(user);
    }

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> insertUser(@RequestBody User user){
        System.err.println(user.getPassword()+user.getUserName());
        return userService.inserUser(user);
    }

    @PostMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<User> getUsersBySearchVo(@RequestBody SearchVo searchVo){
        return userService.getUsersBySearchVo(searchVo);
    }

    @PutMapping(value = "/user",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> updateUser(@RequestBody User user){

        return userService.updateUser(user);
    }

    @DeleteMapping("/user/{userId}")
    @RequiresPermissions("/api/user")
    public Result<User> deleteUser(@PathVariable int userId){
        return userService.deleteUser(userId);
    }

    @GetMapping("/user/{userId}")
    public User getUserByUserId(@PathVariable int userId){

        return userService.getUserByUserId(userId);
    }

    @PostMapping(value = "/userImg",consumes = "multipart/form-data")
    public Result<String> uploadFile(@RequestParam MultipartFile file){
        return userService.uploadUserImg(file);
    }

    @PutMapping(value = "/profile", consumes = "application/json")
    public Result<User> updateUserProfile(@RequestBody User user) {
        return userService.updateUserProfile(user);
    }
}
