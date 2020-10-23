package com.shyfay.injectbeansdynamic.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Notes
 * @Author muxue
 * @Since 7/23/2020
 */
@Slf4j
public class ServiceInvocationHandler<T> implements InvocationHandler {

    private Class<T> typeOfInterface;


    public ServiceInvocationHandler(Class<T> typeOfInterface){
        this.typeOfInterface = typeOfInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this, args);
        }
        log.info("方法调用开始：{}", args);
        List<String> stringArgs = new ArrayList<>();
        for(Object object : args){
            if(object.getClass().equals(String.class)){
                stringArgs.add((String)object + "AAAA");
            }
        }
        return stringArgs.toString();
    }
}
