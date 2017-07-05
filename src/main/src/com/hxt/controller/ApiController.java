package com.hxt.controller;

import com.hxt.service.DetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Huangxiutao on 2017/7/5.
 */
@RequestMapping("/api")
@Controller
public class ApiController {

    @Autowired
    DetectService detectService;

    @RequestMapping("/detect")
    @ResponseBody
    public String detect(){
        String  result=detectService.getDetectResult();
        return result;
    }
}
