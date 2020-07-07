package com.study.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Demo27005
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-07
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Demo27005 {
    public static void main(String[] args){
        SpringApplication.run(Demo27005.class,args);
    }
}