package com.study.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * GatewayJWT7002
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-08
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GatewayJWT7002 {

    public static void main(String[] args){
        SpringApplication.run(GatewayJWT7002.class,args);
    }
}