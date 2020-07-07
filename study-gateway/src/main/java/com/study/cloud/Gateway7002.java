package com.study.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Gateway7002
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Gateway7002 {

    public static void main(String[] args){
        SpringApplication.run(Gateway7002.class,args);
    }
}