package com.huang.sys.service;

import com.huang.entity.sys.User;
import com.huang.sys.model.LoginAO;
import com.huang.sys.model.RegisterAO;

import javax.servlet.http.HttpServletResponse;

/**
 * @Classname UserService
 * @CreatedDate 2024/3/12 11:50
 * @Author Huang
 */
public interface UserService {
    /**
     * 注册用户
     * @param response
     * @param param
     * @return
     */
    String registerUser(HttpServletResponse response, RegisterAO param);

    /**
     * login
     * @param response
     * @param param
     * @return
     */
    String loginUser(HttpServletResponse response, LoginAO param);

    /**
     * 查询用户
     * @param id
     * @return
     */
    User queryUser(Long id);
}
