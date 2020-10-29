package com.shyfay.springmvcwithoutxml;

import com.shyfay.springmvcwithoutxml.config.AppConfig;
import com.shyfay.springmvcwithoutxml.config.RootConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @Notes 为什么这里不使用web.xml配置Servlet也可以启动
 * Servlet容器启动时会扫描，当前应用里面每一个jar包ServletContainerInitializer的实现类
 * 然后容器启动时会调用这个ServletContainerInitializer实现类的onStartup方法
 * 并且会通过这个实现类上的@HandlesTypes注解将需要注入的组件传递过来（具体就是这个注解指定的类型下面的实现类和子接口等）
 * 在onStartup方法里面将@HhandlesTypes注解指定的类型的实现类和子接口都拿到，并开始初始化根容器和子容器，并将这些拿到的实现类和子接口注入到容器里
 * 从而实现SpringMVC的功能
 * Servlet规范暴露了一个SPI
 * 就是实现Servlet规范的WEB项目必须在/META-INF/services里面硬编码一个名称为
 * javax.servlet.ServletContainerInitializer的文件，并且在这个文件里指定ServletContainerInitializer的实现类的包路径
 * 这样Servlet容器在启动时就会根据这个指定的ServletContainerInitializer的实现类去执行上述操作了
 * 当然是可以自己来实现这个实现类的，从而实现一个手写的SpringMVC
 * SpringMVC的javax.servlet.ServletContainerInitializer这个文件位于spring-web-versionxxx.jar这个包里
 * @Author muxue
 * @Since 10/27/2020
 */
public class StartupInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    //根容器配置类，一个项目只有一个root容器
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    //web容器（子容器）配置类，一个web项目可以有多个web容器
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    //获取DispatcherServlet的映射信息
    // / 拦截所有请求（包括静态资源（xx.js,xx.png）），但是不包括*.jsp
    // /* 拦截所有请求；连*.jsp页面都拦截；jsp页面是tomcat的jsp引擎解析的；
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //如果你想为自己的web项目定制化一个DispatcherServlet，那么可以复写这个protected方法
    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext webApplicationContext){
        DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(webApplicationContext);
        //dispatcherServlet.setDetectAllHandlerAdapters(false);
        return dispatcherServlet;
    }
}
