package com.study.cloud.config;

import com.study.cloud.realm.LoginRealm;
import com.study.cloud.redis.RedisSessionDao;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Hashtable;
import java.util.Map;

/**
 * ShiroConfig
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
@Configuration
public class ShiroConfig {
    @Bean
    //shiro过滤器工厂bean
    //需要将DefaultWebSecurityManager注入到factory中
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(defaultWebSecurityManager);
        //权限设置
        Map<String,String> map = new Hashtable<>();
        /*map.put("/index","authc");
        map.put("/manage","perms[manage]");
        map.put("/administrator","roles[administrator]");*/
        map.put("/demo/test","perms[/test]");
        map.put("/test","perms[/test]");
        map.put("/**","anon");
        factoryBean.setFilterChainDefinitionMap(map);
        //设置如果没有通过校验，发送的请求
        //factoryBean.setLoginUrl("/login");
        //设置未授权，发送的请求
        //factoryBean.setUnauthorizedUrl("/unauth");
        return factoryBean;
    }

    @Bean
    //安全管理器
    //将accountRealm的Bean作为参数注入到安全管理器中
    //@Qualifier注解为注入指定一个名字，这里直接写方法名
    public DefaultWebSecurityManager defaultWebSecurityManager(LoginRealm loginRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(loginRealm);
        manager.setSessionManager(sessionManager());
        return manager;
    }

    @Bean
    //我们自己定义的Realm
    public LoginRealm loginRealm(){
        return new LoginRealm();
    }


    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionCookie());
        sessionManager.setSessionDAO(redisSessionDao());
        return sessionManager;
    }

    @Bean
    public SimpleCookie sessionCookie(){
        SimpleCookie cookie = new SimpleCookie();
            cookie.setName("Shared_Session");
        cookie.isHttpOnly();
        cookie.setMaxAge(-1);
        return cookie;
    }

    @Bean
    public RedisSessionDao redisSessionDao(){
        return new RedisSessionDao();
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

}