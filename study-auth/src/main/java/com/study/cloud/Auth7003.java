package com.study.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Auth7003
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
@SpringBootApplication
@EnableCaching
public class Auth7003 {

    public static void main(String[] args){
        SpringApplication.run(Auth7003.class,args);
    }
}