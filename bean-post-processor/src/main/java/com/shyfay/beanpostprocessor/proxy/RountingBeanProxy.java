package com.shyfay.beanpostprocessor.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import java.util.Map;

/**
 * @Notes
 * @Author muxue
 * @Since 7/11/2020
 */
public class RountingBeanProxy {

    public static Object createProxy(Class<?> type, String beanName, Map<String, Object> candidates){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(type);
        proxyFactory.addAdvice(new RountingBeanMethodInterceptor(beanName, candidates));
        return proxyFactory.getProxy();
    }

    static class RountingBeanMethodInterceptor implements MethodInterceptor {

        private static final String DEFAULT_BEAN_NAME = "helloServiceImpl1";

        private Object target;

        public RountingBeanMethodInterceptor(String beanName, Map<String, Object> candidates){
            this.target = candidates.get(beanName);
            if(null == target){
                this.target = candidates.get(DEFAULT_BEAN_NAME);
            }
        }

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            return methodInvocation.getMethod().invoke(this.target, methodInvocation.getArguments());
        }
    }
}
