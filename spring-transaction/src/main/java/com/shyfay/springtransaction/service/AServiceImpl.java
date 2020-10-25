package com.shyfay.springtransaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Notes
 * @Author muxue
 * @Since 7/5/2020
 */
@Service
public class AServiceImpl implements AService{

    @Autowired
    BService bService;

    @Override
    @Transactional(rollbackFor=RuntimeException.class, propagation= Propagation.REQUIRED)
    public void fun1() {
        try {
            bService.fun1();
        } catch (Exception e) {
            System.out.println("afun1...");
        }
    }

    @Override
    @Transactional(rollbackFor=RuntimeException.class, propagation = Propagation.REQUIRED)
    public void fun2() {
        try {
            bService.fun2();
        } catch (Exception e) {
            System.out.println("afun2...");
        }
    }
}
