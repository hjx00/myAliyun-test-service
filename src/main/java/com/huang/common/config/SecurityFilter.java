package com.huang.common.config;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.huang.common.model.BusinessException;
import com.huang.common.utils.JacksonUtils;
import com.huang.common.utils.RedisUtil;
import com.huang.common.utils.RequestTraceUtil;
import com.huang.entity.sys.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Classname SecurityFilter
 * @CreatedDate 2024/3/12 10:18
 * @Author Huang
 */
@Slf4j
public class SecurityFilter extends BasicAuthenticationFilter {
    public SecurityFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil) {
        super(authenticationManager);
        this.redisUtil = redisUtil;
    }
    private RedisUtil redisUtil;

    private final static List<String> ignoreURI = ListUtil.toList("/sys/user/register","/sys/user/login");
    private static  final  UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(new Object(),new Object(), Collections.emptyList());
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        String token = request.getHeader("Authentication");
        String traceId = request.getHeader("traceId");
        if (StrUtil.isBlank(traceId)) {
            traceId = UUID.randomUUID().toString();
        }
        RequestTraceUtil.newTrace(traceId,token,requestURI);
        try {
            if (StringUtils.isEmpty(token)) {
                if (ignoreURI.contains(requestURI)) {
                    SecurityContextHolder.getContext().setAuthentication(loginToken);
                }else SecurityContextHolder.clearContext();
            }else {
                Claims claims = Jwts.parser().setSigningKey("huang").parseClaimsJws(token).getBody();
                String serial = claims.get("serial", String.class);
                User user = JacksonUtils.fromJson(claims.get("user", String.class),User.class);
                String originSerial = (String) redisUtil.get("loginUser:" + user.getPhone());
                if (!Objects.equals(serial,originSerial)) {
                    throw new BusinessException("旧的token");
                }
                RequestTraceUtil.setUserCache(user);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,token, Collections.emptyList()));
            }
            chain.doFilter(request,response);
        } finally {
            RequestTraceUtil.clearCache();
        }
    }
}
