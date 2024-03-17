package com.huang.sys.service.Impl;

import com.huang.common.model.BusinessException;
import com.huang.common.utils.JacksonUtils;
import com.huang.common.utils.RedisUtil;
import com.huang.common.utils.SnowflakeUtil;
import com.huang.entity.sys.User;
import com.huang.sys.mapper.UserMapper;
import com.huang.sys.model.LoginAO;
import com.huang.sys.model.RegisterAO;
import com.huang.sys.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.SHA;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @Classname UserServiceImpl
 * @CreatedDate 2024/3/12 11:52
 * @Author Huang
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;
    /**
     * 注册用户
     *
     * @param response
     * @param param
     * @return
     */
    @Override
    public String registerUser(HttpServletResponse response, RegisterAO param) {
        User user = userMapper.selectByPhone(param.getPhone());
        if (Objects.nonNull(user)) {
            throw new BusinessException("账号已存在");
        }
        LocalDateTime now = LocalDateTime.now();
        user = new User();
        user.setId(SnowflakeUtil.nextId());
        user.setPassword(param.getPassword());
        user.setName(param.getName());
        user.setPhone(param.getPhone());
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setStatus(1);
        user.setIsDelete(false);
        userMapper.insert(user);
        return makeLoginToken(response,user);
    }

    /**
     * login
     *
     * @param response
     * @param param
     * @return
     */
    @Override
    public String loginUser(HttpServletResponse response, LoginAO param) {
        User user = userMapper.selectByPhone(param.getPhone());
        if (Objects.isNull(user)) {
            throw new BusinessException("账号密码错误");
        }
        if (!user.getPassword().equals(param.getPassword())) {
            throw new BusinessException("账号密码错误");
        }
        return makeLoginToken(response, user);
    }

    private String makeLoginToken(HttpServletResponse response, User user) {
        String serial = UUID.randomUUID().toString();
        Claims claims = Jwts.claims();
        claims.put("user", JacksonUtils.toJson(user));
        claims.put("serial",serial);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, "huang").compact();
        response.addCookie(new Cookie("Authentication",token));
        redisUtil.set("loginUser:"+ user.getPhone(),serial,60*60);
        return token;
    }

    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    @Override
    public User queryUser(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        log.info("测试日志，{}",JacksonUtils.toJson(user));
        return user;
    }
}
