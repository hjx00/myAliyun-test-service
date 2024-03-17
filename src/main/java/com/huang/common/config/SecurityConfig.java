package com.huang.common.config;

import com.huang.common.utils.RedisUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Classname SecurityConfig
 * @CreatedDate 2024/3/12 10:25
 * @Author Huang
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private RedisUtil redisUtil;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).httpBasic(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable).logout(AbstractHttpConfigurer::disable)
                .authorizeRequests().anyRequest().authenticated().and()
                .addFilterBefore(new SecurityFilter(authenticationManager(),redisUtil), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new UnAuthenticationException());
    }
}
