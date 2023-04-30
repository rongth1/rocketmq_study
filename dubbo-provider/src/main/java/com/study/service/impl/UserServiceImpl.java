package com.study.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.study.service.IUserService;
import org.springframework.stereotype.Component;

/**
 * @author rth
 * @description: TODO
 * @date 2023/4/30
 */
@Component
@Service(interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService {
    @Override
    public String sayHello() {
        return "hello world ! ";
    }
}
