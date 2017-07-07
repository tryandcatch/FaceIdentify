package com.hxt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;

/**
 * Created by Huangxiutao on 2017/7/5.
 */
@SpringBootApplication
@EnableAutoConfiguration
@Configuration
public class AppStart {
    public static void main(String[] args){
        SpringApplication.run(AppStart.class,args);
        System.out.println("已启动>>>>>>>>>>>>");
    }
    /*@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("D://FaceIdentify//");
        factory.setMaxFileSize("5MB");
        factory.setMaxRequestSize("5MB");
        return factory.createMultipartConfig();
    }*/

}
