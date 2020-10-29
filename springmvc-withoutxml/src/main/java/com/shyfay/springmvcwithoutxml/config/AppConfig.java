package com.shyfay.springmvcwithoutxml.config;

import com.shyfay.springmvcwithoutxml.pojo.Child;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Notes 子容器
 * @Author muxue
 * @Since 10/27/2020
 */
@ComponentScan(value="com.shyfay.springmvcwithoutxml", useDefaultFilters = false,
    includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes={Controller.class, ControllerAdvice.class, RestControllerAdvice.class, Service.class})}
)
@Configuration
public class AppConfig {


    //容器里注册的bean，只能在子容器里使用
    //比如这里注入的bean在Service就无法使用，如果在Service里使用@Autowired注入会启动报错
    //因为该子容器指负责扫描controller控制器
    @Bean
    public Child getChildBean(){
        Child child = new Child();
        child.setId("1");
        child.setName("shyfay");
        return child;
    }
}
