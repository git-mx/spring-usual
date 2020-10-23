package com.shyfay.beanpostprocessor.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Notes
 * @Author muxue
 * @Since 7/11/2020
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RountingInjected {
    String value() default "helloServiceImpl1";
}
