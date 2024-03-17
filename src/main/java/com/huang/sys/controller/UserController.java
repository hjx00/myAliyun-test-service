package com.huang.sys.controller;

import com.huang.common.model.Wrapper;
import com.huang.entity.sys.User;
import com.huang.sys.model.LoginAO;
import com.huang.sys.model.RegisterAO;
import com.huang.sys.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * @Classname UserController
 * @CreatedDate 2024/3/11 15:30
 * @Author Huang
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Resource
    private UserService userService;
    /**
     * 注册用户
     * @return
     */
    @PostMapping("/register")
    public Wrapper<String> registerUser(HttpServletResponse response, @Valid @RequestBody RegisterAO param){
           String token =  userService.registerUser(response,param);
        return Wrapper.success(token);
    }

    /**
     * 注册用户
     * @return
     */
    @PostMapping("/login")
    public Wrapper<String> loginUser(HttpServletResponse response,  @RequestBody @Valid LoginAO param){
        String token =  userService.loginUser(response,param);
        return Wrapper.success(token);
    }

    /**
     * 查询用户
     * @return
     */
    @PostMapping("/getUser")
    public Wrapper<User> queryUser(@RequestBody Long id ){
        User user =  userService.queryUser(id);
        return Wrapper.success(user);
    }

}
