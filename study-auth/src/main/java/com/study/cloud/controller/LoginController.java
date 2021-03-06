package com.study.cloud.controller;

import com.study.cloud.service.LoginService;
import com.study.cloud.utils.RedisUtil;
import com.study.entities.UserEntity;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;
import java.util.Set;

import static org.apache.shiro.SecurityUtils.getSecurityManager;
import static org.apache.shiro.SecurityUtils.getSubject;

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

    @Autowired
    private LoginService loginService;

    private static final String KEY_PREFIX = "shiro_redis_session:";

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/login")
    public UserEntity login(String username, String password){
        if (StringUtils.isBlank(username)) {
            //return "Please input username";
            return null;
        }
        if (StringUtils.isBlank(password)) {
            //return "Please input password";
            return null;
        }
        UserEntity login = loginService.login(username, password);
        System.out.println(login);
        System.out.println("==============================");
        return login;
    }

    @PostMapping("/show")
    public Object showSession(){
        Session session = getSubject().getSession();
        UserEntity userEntity = (UserEntity)session.getAttribute("userEntity");
        System.out.println(userEntity);
        if(userEntity == null) {
            return "权限没有了！";
        }
        return userEntity;
    }

    @PostMapping("/logout")
    public Object logout(){
        loginService.logout();
        return null;
    }

    @PostMapping("/checkPermission")
    @ResponseBody
    public UserEntity checkPermission(HttpServletRequest request, @RequestParam("sessionId") String sessionId, @RequestParam("checkUrl") String checkUrl){
        //Session session = getSubject().getSession();
        /*if(oldSession==null){
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setLoginName("权限没有了1");
            return userEntity1;
        }
        UserEntity userEntity = (UserEntity) oldSession.getAttribute("userEntity");
        if(userEntity == null){
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setLoginName("权限没有了2");
            return userEntity1;
        }*/
        /*UserEntity userEntity = (UserEntity) oldSession.getAttribute("userEntity");
        UsernamePasswordToken token = new UsernamePasswordToken(userEntity.getLoginName(),userEntity.getLoginPasswd());
        SecurityUtils.getSubject().checkPermission("/test");
        SecurityUtils.getSubject().getPrincipal();
        SecurityUtils.getSubject().getSession().setAttribute("userEntity",userEntity);
        Session session = getSubject().getSession();
        session.setAttribute("userEntity",userEntity);
        boolean permitted = getSubject().isPermitted("/test");
        PrincipalCollection previousPrincipals = getSubject().getPreviousPrincipals();
        boolean permitted1 = getSecurityManager().isPermitted(previousPrincipals, "/test");*/

        String key = KEY_PREFIX + sessionId;
        Session oldSession = (Session) redisUtil.get(key);
        UserEntity userEntity = (UserEntity) oldSession.getAttribute("userEntity");
        Set<String> permission = userEntity.getPermission();
        Boolean contains = permission.contains(checkUrl);
        if(contains){
            return userEntity;
        }
        System.out.println("权限没有了3");
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setLoginName("权限没有了3");
        return userEntity1;
    }
}