package com.study.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Demo7004
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-07
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Demo7004 {
    public static void main(String[] args){
        SpringApplication.run(Demo7004.class,args);
    }
}