package com.huang.common.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Classname RedisUtil
 * @CreatedDate 2024/3/13 14:31
 * @Author Huang
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;
    
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public <V> List<V> multiGet(List<String> keys){
        return (List<V>) redisTemplate.opsForValue().multiGet(keys);
    }

    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public <K, V> void putAll(String key, Map<K, V> value){
        redisTemplate.opsForHash().putAll(key, value);
    }

    public <K, V> V get(String key, K hashKey){
        return (V) redisTemplate.opsForHash().get(key, hashKey);
    }

    public <K, V> Map<K, V> getHash(String key){
        return (Map<K, V>) redisTemplate.opsForHash().entries(key);
    }

    public void set(String key, String hashKey, Object value){
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public <V> List<V> hashValues(String key){
        return (List<V>) redisTemplate.opsForHash().values(key);
    }

    public void set(String  key, Object value, long expireSeconds){
        redisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
    }

    public Object getAndSet(String key, Object value){
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public Long getAndIncrement(String key, long delta){
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Boolean setIfAbsent(String key, Object value){
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }

    public Long delete(List<String> keys){
        return redisTemplate.delete(keys);
    }

    public Long deleteHash(String key, Object... hashKey){
        return redisTemplate.opsForHash().delete(key, hashKey);
    }

    public Boolean expire(String key, long expireSeconds){
        return redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
    }

    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public Long getExpireSeconds(String key){
        return redisTemplate.opsForValue().getOperations().getExpire(key, TimeUnit.SECONDS);
    }

    public <R> R execute(String script, Class<R> returnType, List<String> keys, Object... args){
        if(keys == null){
            throw new IllegalArgumentException("keys not allow null");
        }
        DefaultRedisScript<R> redisScript = new DefaultRedisScript<>(script, returnType);
        return (R) redisTemplate.execute(redisScript, keys, args);
    }

    public <R> R executeWithSource(String luaSource, Class<R> returnType, List<String> keys, Object... args){
        if(keys == null){
            throw new IllegalArgumentException("keys not allow null");
        }
        DefaultRedisScript<R> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(luaSource)));
        redisScript.setResultType(returnType);
        return (R) redisTemplate.execute(redisScript, keys, args);
    }

    public <V> Long pushAllList(String key ,List<V> value){
        return redisTemplate.opsForList().leftPushAll(key, value.toArray());
    }

    public <V> Long pushList(String key ,V value){
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public Object popList(String key){
        return redisTemplate.opsForList().rightPop(key);
    }

    public <V> List<V> rangeList(String key, long start, long end){
        return (List<V>) redisTemplate.opsForList().range(key, start, end);
    }

    public Boolean persist(String key){
        return redisTemplate.persist(key);
    }

    public List<Object> executePipelinedForGetHash(List<String> keys){
        SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (String key : keys) {
                    getHash(key);
                }
                // 必须返回 null
                return null;
            }
        };

        return redisTemplate.executePipelined(sessionCallback);
    }
    
}
