package com.shyfay.springtransaction.service;

/**
 * @Notes 用于解决在同一个类中fun1调用fun2导致fun2的事务注解不生效的问题
 * @Author muxue
 * @Since 7/8/2020
 */
public interface CService {
    void fun1();

    void fun2();
}
