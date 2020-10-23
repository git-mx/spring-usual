package com.shyfay.beanpostprocessor.service;

import org.springframework.stereotype.Service;

/**
 * @Notes
 * @Author muxue
 * @Since 7/9/2020
 */
@Service
public class HelloServiceImpl1 implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("Hello i am HelloServiceImpl1...");
    }
}
