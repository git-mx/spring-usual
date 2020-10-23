package com.shyfay.injectbeansdynamic.factory;

import com.shyfay.injectbeansdynamic.proxy.ServiceInvocationHandler;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Notes
 * @Author muxue
 * @Since 7/23/2020
 */
public class ServiceFactoryBean<T> implements FactoryBean {

    private Class<T> typeOfInterface;

    public ServiceFactoryBean(Class<T> typeOfInterface){
        this.typeOfInterface = typeOfInterface;
    }

    @Override
    public Object getObject() throws Exception {
        InvocationHandler invocationHandler = new ServiceInvocationHandler(typeOfInterface);
        return Proxy.newProxyInstance(typeOfInterface.getClassLoader(), new Class[]{typeOfInterface}, invocationHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return typeOfInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
