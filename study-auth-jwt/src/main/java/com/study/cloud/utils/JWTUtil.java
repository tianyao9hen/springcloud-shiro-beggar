package com.study.cloud.utils;

import com.alibaba.fastjson.JSONArray;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.util.Map;

public class JWTUtil {

    private static final String KEY = "tokenKey";

    public static String createToken(Map<String, Object> map,int expireMinutes) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder().setHeaderParam("type", "JWT")
                .setClaims(map)
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(signatureAlgorithm,KEY.getBytes());
        return builder.compact();
    }


    public static Claims parseToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(KEY.getBytes())
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}