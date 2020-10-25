package com.shyfay.springtransaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Notes AAAA
 * @Author muxue
 * @Since 7/5/2020
 */
@Service
public class BServiceImpl implements BService {


    @Override
    @Transactional(rollbackFor=Exception.class, propagation= Propagation.REQUIRES_NEW)
    public void fun1() throws RuntimeException {
        System.out.println("1111");
        throw new RuntimeException("fun1 exception...");
    }

    @Override
    @Transactional(rollbackFor=Exception.class, propagation = Propagation.NESTED)
    public void fun2() throws RuntimeException {
        System.out.println("2222");
        throw new RuntimeException("fun2 exception...");
    }
}
