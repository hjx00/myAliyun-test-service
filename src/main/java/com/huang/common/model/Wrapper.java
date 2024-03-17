package com.huang.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Classname Wrapper
 * @CreatedDate 2024/3/12 9:27
 * @Author Huang
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  class Wrapper<T> implements Serializable {
    private int code;
    private String error;
    private long timestamp;
    private T data;

    public  static <T> Wrapper<T> success(T data){
       return new Wrapper<>(200,"",System.currentTimeMillis(),data);
    }
    public  static <T> Wrapper<T> success(){
        return new Wrapper<>(200,"",System.currentTimeMillis(),null);
    }
    public  static <T> Wrapper<T> error(String error){
        return new Wrapper<>(500,error,System.currentTimeMillis(),null);
    }
    public  static <T> Wrapper<T> warn(String error){
        return new Wrapper<>(400,error,System.currentTimeMillis(),null);
    }
}
