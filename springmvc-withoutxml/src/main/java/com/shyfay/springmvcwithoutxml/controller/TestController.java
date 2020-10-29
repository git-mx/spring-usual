package com.shyfay.springmvcwithoutxml.controller;

import com.shyfay.springmvcwithoutxml.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Notes
 * @Author muxue
 * @Since 10/27/2020
 */
@Controller
public class TestController {

    @Autowired
    TestService testService;

    @ResponseBody
    @RequestMapping(value = "/getParentMessage", method = RequestMethod.GET)
    public String getParentMessage(){
        return testService.getParentMessage();
    }

    @ResponseBody
    @RequestMapping(value = "/getChildMessage", method = RequestMethod.GET)
    public String getChildMessage(){
        return testService.getChildMessage();
    }
}
