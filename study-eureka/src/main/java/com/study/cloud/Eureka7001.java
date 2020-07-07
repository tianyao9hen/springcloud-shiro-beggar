package com.study.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka7001
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka7001 {
    public static void main(String[] args){
        SpringApplication.run(Eureka7001.class,args);
    }
}