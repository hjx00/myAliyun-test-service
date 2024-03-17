package com.huang.common.config;


import com.huang.common.model.Wrapper;
import com.huang.common.utils.JacksonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname UnAuthenticationException
 * @CreatedDate 2023/10/31 14:10
 * @Author Huang
 */
public class UnAuthenticationException  implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("Application/json");
        httpServletResponse.getWriter().print(JacksonUtils.toJson(new Wrapper<>(401,"请先登陆",System.currentTimeMillis(),null)));
    }
}
