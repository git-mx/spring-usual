package com.shyfay.beanpostprocessor.component;

import com.shyfay.beanpostprocessor.annotation.RountingInjected;
import com.shyfay.beanpostprocessor.proxy.RountingBeanProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Notes
 * @Author muxue
 * @Since 7/11/2020
 */
@Component
public class RountingBeanPostProcessor implements ApplicationContextAware, BeanPostProcessor {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        Class<?> clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(RountingInjected.class)){
                if(!field.getType().isInterface()){
                    throw new BeanCreationException("The RountingInjceted field must be decalred as an inferface: " + field.getName() + "@Class" + clazz.getName());
                }
                try {
                    this.handleRountingInjectedField(field, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return o;
    }

    private void handleRountingInjectedField(Field field, Object bean) throws IllegalAccessException {
        Class typeOfField = field.getType();
        Map<String, Object> candidates = this.applicationContext.getBeansOfType(typeOfField);
        field.setAccessible(true);
        if(1 == candidates.size()){
            field.set(bean, candidates.values().iterator().next());
        }else if(2 == candidates.size()){
            String injectedBeanName = field.getAnnotation(RountingInjected.class).value();
            Object proxy = RountingBeanProxy.createProxy(typeOfField, injectedBeanName, candidates);
            field.set(bean, proxy);
        }else{
            throw new IllegalAccessException("Found more than 2 implements of type " + typeOfField.getName());
        }
    }
}
