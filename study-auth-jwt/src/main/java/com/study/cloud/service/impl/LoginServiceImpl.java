package com.study.cloud.service.impl;

import com.study.cloud.mapper.LoginMapper;
import com.study.cloud.service.LoginService;
import com.study.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * LoginServiceImpl
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public UserEntity byLoginName(String loginName) {
        UserEntity userEntity = loginMapper.queryUserByLoginName(loginName);
        return userEntity;
    }

    @Override
    public UserEntity login(String loginName, String password) {
        UserEntity userEntity = loginMapper.queryUserByLoginName(loginName);
        if(userEntity!=null && userEntity.getLoginPasswd().equals(password)){
            Set<String> permission = new HashSet<>();
            permission.add("/test");
            permission.add("2");
            permission.add("3");
            userEntity.setPermission(permission);
            return userEntity;
        }
        return null;
    }

    @Override
    public int logout() {
        return 0;
    }

}