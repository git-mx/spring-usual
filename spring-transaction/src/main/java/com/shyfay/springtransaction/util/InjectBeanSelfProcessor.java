package com.shyfay.springtransaction.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Notes
 * @Author muxue
 * @Since 7/8/2020
 */
@Component
public class InjectBeanSelfProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if(o instanceof BeanSelfAware){
            ((BeanSelfAware)o).setSelf(o);
        }
        return o;
    }
}
