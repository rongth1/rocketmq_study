package com.study.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.study.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rth
 * @description: TODO
 * @date 2023/4/30
 */
@RestController
public class HelloController {

    @Reference
    private IUserService userService;

    @GetMapping("/hello")
    public String sayHello() {
        return userService.sayHello();
    }
}
