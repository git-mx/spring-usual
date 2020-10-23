package com.shyfay.beanpostprocessor.controller;

import com.shyfay.beanpostprocessor.annotation.RountingInjected;
import com.shyfay.beanpostprocessor.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Notes BeanPostProcessor测试
 * 本测试用例用于演示BeanPostProcessor的作用，就是在Bean实例化之后对bean进行一些操作和处理
 * 本例想要达到的效果是：有一个interface，同时有两个implementer(实现类),在引用这个interface的地方
 * 通过一个注解来指定要引用的是哪一个实现类。
 * 具体的实现原理是自定义一个BeanPostProcessor，这样Spring在启动时实例化持有这个interface的类时
 * （本例种就是BeanPostProcessorController）会调用BeanPostRrocessor这个类的postProcessAfterInitialization
 * 方法，在这个方法里我们拿到被@RountingInjected注解作用的域(Field,本例中就是helloService)，并且拿到注解
 * 的value(本例中是helloServiceImpl2)，然后通过这个value在Spring上下文中找到对应的实现类，然后基于这个
 * 实现类创建一个代理，最后将这个Field注入成为代理，这样当调用这个interface的某个方法的时实际上是代理对象来调用的
 * 本例涉及到的知识点有：
 * 1.自定义注解
 * 2.JAVA反射
 * 3.ProxyFactory代理工厂的使用
 * 4.MehtodInterceptor方法拦截
 * 5.BeanPostProcessor（在实例化bean之后对bean进行一系列操作）
 * @Author muxue
 * @Since 7/8/2020
 */
@RestController
@RequestMapping("/beanPostProcessor")
public class BeanPostProcessorController {

    @RountingInjected(value="helloServiceImpl2")
    private HelloService helloService;

    @GetMapping(value="/sayHello")
    public String sayHello(){
        helloService.sayHello();
        return "success";
    }

}
