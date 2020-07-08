package com.study.cloud.controller;

import com.study.cloud.service.LoginService;
import com.study.cloud.utils.JWTUtil;
import com.study.entities.UserEntity;
import io.jsonwebtoken.Claims;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * LoginController
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    private static final String priKeyPath = "D:\\ras\\ras.pri";

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public UserEntity login(String username, String password) throws Exception {
        UserEntity userEntity = loginService.login(username, password);
        if(userEntity==null){
            return null;
        }
        String token = null;
        Map<String,Object> map = new HashMap();
        map.put("loginName",userEntity.getLoginName());
        token = JWTUtil.createToken(map, 30);
        userEntity.setToken(token);
        Claims claims = JWTUtil.parseToken(token);
        userEntity.setClain(claims);

        return userEntity;
    }

    @PostMapping("/logout")
    public Object logout(){
        loginService.logout();
        return null;
    }

}