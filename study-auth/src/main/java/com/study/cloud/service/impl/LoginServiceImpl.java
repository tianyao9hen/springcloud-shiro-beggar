package com.study.cloud.service.impl;

import com.study.cloud.mapper.LoginMapper;
import com.study.cloud.service.LoginService;
import com.study.entities.UserEntity;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName,password);
        try{
            subject.login(token);
            //将用户信息传递到主页

            //得到account信息
            UserEntity userEntity = (UserEntity) subject.getPrincipal();
            Set<String> permission = new HashSet<>();
            permission.add("/test");
            permission.add("2");
            permission.add("3");
            userEntity.setPermission(permission);
            //得到session,并向session中存放用户信息
            subject.getSession().setAttribute("userEntity",userEntity);
            return userEntity;
        }catch(UnknownAccountException e){
            //用户名不存在
            return null;
        }catch(IncorrectCredentialsException e){
            //密码错误
            return null;
        }
    }

    @Override
    public int logout() {
        SecurityUtils.getSubject().logout();
        System.out.println("----------------退出");
        return 0;
    }

}