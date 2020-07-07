package com.study.cloud;

import com.study.entities.UserEntity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;

/**
 * LoginCheckApiFallback
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-07
 */
//@Component
public class LoginCheckApiFallback implements LoginCheckApi {

    @Override
    public UserEntity getUserInfo() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLoginName("getUserInfo服务降级");
        return userEntity;
    }

    @Override
    public UserEntity checkPermission(String sessionId, String checkUrl) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLoginName("checkPermission服务降级");
        return userEntity;
    }
}