package com.shyfay.springmvcwithoutxml.service;

import com.shyfay.springmvcwithoutxml.pojo.Child;
import com.shyfay.springmvcwithoutxml.pojo.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Notes
 * @Author muxue
 * @Since 10/27/2020
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    Parent parent;

    @Autowired
    Child child;

    @Override
    public String getParentMessage() {
        return parent.getId() + "," + parent.getName();
    }

    @Override
    public String getChildMessage() {
//        Child child = new Child();
//        child.setId("1");
//        child.setName("child");
        return child.getId() + "," + child.getName();
    }
}
