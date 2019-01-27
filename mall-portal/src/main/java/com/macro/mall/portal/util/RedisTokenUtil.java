package com.macro.mall.portal.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${jwt.expiration}")
    private Long expiration;

    private static final String LOGIN_TOKENS = "login.tokens.%s";

    private static String getLoginKey(String token) {
        return String.format(LOGIN_TOKENS, token);
    }

    public void setToken(String token, UserDetails userDetails) {
        redisTemplate.opsForValue().set(getLoginKey(token), JsonUtil.objectToJson(userDetails), expiration);
    }

    public void removeToken(String authToken) {
        redisTemplate.delete(getLoginKey(authToken));
    }

    public void hset(String key, String filed, Object domain, Long expire) {
        redisTemplate.opsForHash().put(key, filed, domain);
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public boolean hasToken(String authToken) {
        return redisTemplate.hasKey(getLoginKey(authToken));
    }

    public void setTokenRefresh(String token, String username, UserDetails userDetails) {
        //刷新时间
//        hset(token, "tokenValidTime", DateUtil.getAddDayTime(validTime), expire);
//        hset(token, "expirationTime", DateUtil.getAddDaySecond(expirationSeconds), expire);
        hset(getLoginKey(token), "username", username, expiration);
        hset(getLoginKey(token), "userDetails", JsonUtil.objectToJson(userDetails), expiration);
//        hset(token, "ip", ip, expire);
    }

    public String getUsernameByToken(String token) {
        return (String) redisTemplate.opsForHash().get(getLoginKey(token), "username");
    }

    public UserDetails getUserDetails(String token) {
        Object userDetails = redisTemplate.opsForHash().get(getLoginKey(token), "userDetails");
        return JsonUtil.jsonToPojo((String) userDetails, UserDetails.class);
    }
}
