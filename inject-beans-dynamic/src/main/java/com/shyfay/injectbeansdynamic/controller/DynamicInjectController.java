package com.shyfay.injectbeansdynamic.controller;

import com.shyfay.injectbeansdynamic.service.DService;
import com.shyfay.injectbeansdynamic.service.EService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Notes 本例用于演示如何生成代理类并动态注入SpringIOC容器
 * 本例为两个没有实现类的interface生成了两个代理类并将这两个代理类动态注入到Spring容器中
 * 本例涉及到的知识点有
 * 1.使用ResourceLoaderAware实现包扫描功能
 * 2.使用InvocationHandler动态代理
 * 3.FactoryBean的使用，实际上FactoryBean生成实例化对象都是通过FactoryBean的getObject来操作的
 * 4.使用BeanDefinitionRegistryPostProcessor动态注入bean到Spring容器
 * @Author muxue
 * @Since 7/10/2020
 */
@RestController
@RequestMapping("/dynamicInject")
public class DynamicInjectController {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private DService dService;

    @Autowired
    private EService eService;

    @GetMapping("/test")
    public String getHello() {
        String testList = dService.getList("code123","name456");
        String calculateResult = eService.getResult("测试");
        return (testList + "," + calculateResult);
    }

}
