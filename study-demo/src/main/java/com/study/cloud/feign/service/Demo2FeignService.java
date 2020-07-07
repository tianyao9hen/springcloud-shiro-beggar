package com.study.cloud.feign.service;

import com.study.cloud.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Demo2FeignService
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-07
 */
@Service
@FeignClient(value = "STUDY-DEMO2",configuration = FeignConfig.class)
@RequestMapping("/demo2")
public interface Demo2FeignService {

    @PostMapping("/get")
    public String getDemo2();

}