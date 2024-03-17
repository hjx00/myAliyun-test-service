package com.huang.sys.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname LoginAO
 * @CreatedDate 2024/3/12 12:17
 * @Author Huang
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginAO implements Serializable {

    /**
     * 手机号
     */
    @NotNull(message = "手机号码不能为空")
    @Size(min = 1,message ="密码不能为空")
    private String phone;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @Size(min = 1,message ="密码不能为空")
    private String password;

    private LocalDateTime date;
}
