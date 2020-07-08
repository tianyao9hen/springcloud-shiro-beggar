package com.study.cloud.service;


import com.study.entities.UserEntity;

/**
 * LoginService
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
public interface LoginService {

    public UserEntity byLoginName(String loginName);


    UserEntity login(String username, String password);

    int logout();
}