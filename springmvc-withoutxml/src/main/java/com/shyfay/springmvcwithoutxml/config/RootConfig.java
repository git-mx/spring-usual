package com.shyfay.springmvcwithoutxml.config;

import com.shyfay.springmvcwithoutxml.pojo.Parent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Notes 父容器
 * @Author muxue
 * @Since 10/27/2020
 */
@ComponentScan(value = "com.shyfay.springmvcwithoutxml", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, ControllerAdvice.class, RestControllerAdvice.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AppConfig.class}),
})
@Configuration
public class RootConfig {

    @Bean
    public Parent getParentBean(){
        Parent parent = new Parent();
        parent.setId("1");
        parent.setName("shyfay");
        return parent;
    }

}
