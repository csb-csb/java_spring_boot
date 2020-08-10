package com.sfac.javaSpringBoot.modules.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
public class TestController {
@GetMapping("/testDesc")
@ResponseBody//代表接口返回类型
public String testDesc(){
    return "This is test module desc.";
}

}
