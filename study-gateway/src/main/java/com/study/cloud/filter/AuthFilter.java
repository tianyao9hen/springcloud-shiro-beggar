package com.study.cloud.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.cloud.LoginCheckApi;
import com.study.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * AuthFilter
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-07
 */
@Service
@Slf4j
public class AuthFilter implements GlobalFilter,Ordered{

    @Autowired
    private LoginCheckApi loginCheckApi;
    
    private static Set<String> urlSet = new HashSet<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("************************ AuthFilter: "+new Date());
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            chain.filter(exchange);
        }
        String end  = "";
        if(path.lastIndexOf("/") >= 0){
            urlSet.add("/login");
            urlSet.add("/logout");
            urlSet.add("/show");
            urlSet.add("/checkPermission");
            end  = path.substring(path.lastIndexOf("/"));
            if(urlSet.contains(end)) {
                return chain.filter(exchange);
            }
        }

        MultiValueMap<String, HttpCookie> cookieMultiValueMap =  request.getCookies();
        HttpCookie cookie = cookieMultiValueMap.getFirst("Shared_Session");
        if(cookie == null){
            ServerHttpResponse response = exchange.getResponse();
            JSONObject message = new JSONObject();
            message.put("status", -1);
            message.put("data", "鉴权失败");
            byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        String value = cookie.getValue();
        UserEntity userEntity = loginCheckApi.checkPermission(value, end);
        System.out.println(userEntity.toString());
        String json = JSONObject.toJSONString(userEntity);
        /*ServerHttpRequest httpRequest = exchange.getRequest().mutate().header("userEntity", json).build();
        ServerWebExchange build = exchange.mutate().request(httpRequest).build();*/
        try {
            json = URLEncoder.encode(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String finalJson = json;
        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            httpHeader.set("userEntity", finalJson);
        };
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
        exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {


        return 0;
    }
}