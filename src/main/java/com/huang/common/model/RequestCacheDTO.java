package com.huang.common.model;

import com.huang.entity.sys.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Classname UserCacheDTO
 * @CreatedDate 2024/3/14 15:39
 * @Author Huang
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCacheDTO implements Serializable {
    private User user;
    private String traceId;
    private String uri;
    private String token;
}
