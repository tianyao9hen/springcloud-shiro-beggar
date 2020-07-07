package com.study.cloud.realm;

import com.study.cloud.service.LoginService;
import com.study.entities.UserEntity;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginRealm
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(userEntity.getPermission());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserEntity userEntity = loginService.byLoginName(token.getUsername());
        if(userEntity!=null)
            return new SimpleAuthenticationInfo(userEntity,userEntity.getLoginPasswd(),getName());

        return null;
    }
}