package com.shyfay.springtransaction.service;

import com.shyfay.springtransaction.util.BeanSelfAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Notes
 * @Author muxue
 * @Since 7/8/2020
 */
@Service
public class CServiceImpl implements CService, BeanSelfAware {
    private CService proxySelf;
    @Override
    public void setSelf(Object proxyBean) {
        this.proxySelf = (CService) proxyBean;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void fun1() {
        System.out.println("CService fun1 ...");
        proxySelf.fun2();
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void fun2() {
        System.out.println("CService fun2 ...");
    }
}
