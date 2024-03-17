package com.huang.common.utils;

import cn.hutool.core.lang.Snowflake;

/**
 * @Classname SnowflakeUtil
 * @CreatedDate 2024/3/14 14:37
 * @Author Huang
 */
//@Component
public class SnowflakeUtil {

    private static final Long workerId = 1L;
    private static final Long dataCenterId = 1L;
    private static final Snowflake  snowflake ;
    static {
        snowflake = new Snowflake(workerId,dataCenterId);
    }

    public static Long nextId(){
        return snowflake.nextId();
    }
}
