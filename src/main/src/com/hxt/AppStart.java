package com.hxt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Huangxiutao on 2017/7/5.
 */
@SpringBootApplication
public class AppStart {
    public static void main(String[] args){
        SpringApplication.run(AppStart.class,args);
        System.out.println("已启动>>>>>>>>>>>>");
    }
}
