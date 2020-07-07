package com.study.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.cloud.feign.service.Demo2FeignService;
import com.study.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * DemoController
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-07
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private Demo2FeignService demo2FeignService;

    @PostMapping("/test")
    public String demo(HttpServletRequest request) throws UnsupportedEncodingException {
        String userEntityJson = request.getHeader("userEntity");
        String decode = URLDecoder.decode(userEntityJson, "utf-8");
        if(userEntityJson == null){
            return "没有userEntity请求头";
        }
        UserEntity userEntity = JSONObject.parseObject(decode, UserEntity.class);
        System.out.println("++++++++++++++++++++++++++++");
        System.out.println(userEntity.toString());
        String demo2 = demo2FeignService.getDemo2();
        return userEntity.getUserName() + ": 获取demo.test : "+demo2;
    }
}