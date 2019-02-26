package com.macro.mall.portal.component;

import com.macro.mall.portal.util.JsonUtil;
import com.macro.mall.portal.util.JwtTokenUtil;
import com.macro.mall.portal.util.RedisTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisTokenUtil redisTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);
        redisTokenUtil.setTokenRefresh(token, userDetails.getUsername(), userDetails);
        token = tokenHead + token;
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setHeader(tokenHeader, token);
        Map<String, Object> maps = new HashMap<>();
        maps.put("code", 200);
        maps.put("message", "登录成功");
        maps.put(tokenHeader, token);
        response.getWriter().print(JsonUtil.objectToJson(maps));
        response.getWriter().flush();
    }
}
