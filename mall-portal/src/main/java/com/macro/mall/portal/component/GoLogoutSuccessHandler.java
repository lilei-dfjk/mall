package com.macro.mall.portal.component;

import com.macro.mall.portal.util.RedisTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by macro on 2018/8/6.
 */
public class GoLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private RedisTokenUtil redisTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authHeader = request.getHeader(tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length());
            redisTokenUtil.removeToken(authToken);
        }
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().print("{\"code\":200,\"message\":\"已注销\"}");
        response.getWriter().flush();
    }
}
