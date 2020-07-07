package com.study.cloud;

import com.study.entities.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * LoginCheckApi
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-07
 */
@Component
//@FeignClient(value = "STUDY-AUTH",fallback = LoginCheckApiFallback.class)
@FeignClient("STUDY-AUTH")
@RequestMapping("/auth")
public interface LoginCheckApi {

    @PostMapping("/getUserInfo")
    public UserEntity getUserInfo();

    @PostMapping("/checkPermission")
    public UserEntity checkPermission(@RequestParam("sessionId") String sessionId, @RequestParam("checkUrl") String checkUrl);
}