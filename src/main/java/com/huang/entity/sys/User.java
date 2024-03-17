package com.huang.entity.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Classname User
 * @CreatedDate 2024/3/11 15:27
 * @Author Huang
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String password;
    private String phone;
    private Integer status;
    private Boolean isDelete;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
