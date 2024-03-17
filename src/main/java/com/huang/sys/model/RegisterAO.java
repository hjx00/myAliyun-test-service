package com.huang.sys.model;

import lombok.*;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Classname RegisterAO
 * @CreatedDate 2024/3/12 9:45
 * @Author Huang
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAO implements Serializable {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    /**
     * 手机号
     */
    @NotBlank(message = "名称不能为空")
    private String name;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
