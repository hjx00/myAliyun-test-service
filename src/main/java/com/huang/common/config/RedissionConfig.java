package com.huang.common.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RedissionConfig
 * @CreatedDate 2024/3/15 10:42
 * @Author Huang
 */
@Configuration
public class RedissionConfig {

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://8.138.100.155:16380").setPassword("huang52131004")
                .setDatabase(1).setTimeout(3000).setConnectionPoolSize(100);
        config.setLockWatchdogTimeout(60000);
        config.setCodec(JsonJacksonCodec.INSTANCE);
        config.setThreads(4);
        config.setNettyThreads(16);
        config.setKeepPubSubOrder(true);
        return Redisson.create(config);
    }
}
