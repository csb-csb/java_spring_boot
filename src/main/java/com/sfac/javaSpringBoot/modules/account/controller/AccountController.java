package com.sfac.javaSpringBoot.modules.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {


    /**
     * 127.0.0.1/account/login ---- get
     */
    /**
     * 127.0.0.1/account/login ---- get
     */
    @GetMapping("/login")
    public String loginPage() {
        return "indexSimple";
    }
    /**
     * 127.0.0.1/account/register ---- get
     */
    @GetMapping("/register")
    public String registerPage() {
        return "indexSimple";
    }

    @GetMapping("/users")
    public String usersPage(){
        return "index";
    }
    @GetMapping("/da")
    public String userssPage(){
        return "index";
    }

    @GetMapping("/profile")
    public String profile(){
        return "index";
    }
}
