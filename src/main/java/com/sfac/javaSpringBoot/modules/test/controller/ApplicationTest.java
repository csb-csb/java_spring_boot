package com.sfac.javaSpringBoot.modules.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/ApplicationTest.properties")
@ConfigurationProperties(prefix = "com.qq")
public class ApplicationTest {
    //Value读取配置文件的值
  //  @Value("${server.port}")
    private int port;
  //  @Value("${com.name}")
    private String name;
   // @Value("${com.age}")
    private int age;
  //  @Value("${com.desc}")
    private String desc;
  //  @Value("${com.random}")
    private String random;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }
}
