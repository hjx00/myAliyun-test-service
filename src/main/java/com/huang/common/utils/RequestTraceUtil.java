package com.huang.common.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.huang.common.model.RequestCacheDTO;
import com.huang.entity.sys.User;

/**
 * @Classname RequestTraceUtil
 * @CreatedDate 2024/3/14 14:10
 * @Author Huang
 */
public class RequestTraceUtil {

    private static  final  TransmittableThreadLocal<RequestCacheDTO> requestCacheThreadLocal = new TransmittableThreadLocal<>();

    public static void setUserCache(User userCache){
        requestCacheThreadLocal.get().setUser(userCache);
    }
    public static User getUserCache(){
        if (requestCacheThreadLocal.get()==null) {
            return null;
        }
       return requestCacheThreadLocal.get().getUser();
    }
    public static void newTrace(String traceId,String token,String uri){
        RequestCacheDTO requestCacheDTO = new RequestCacheDTO();
        requestCacheDTO.setTraceId(traceId);
        requestCacheDTO.setUri(uri);
        requestCacheDTO.setToken(token);
        requestCacheThreadLocal.set(requestCacheDTO);
    }
    public static String getTraceId(){
        if (requestCacheThreadLocal.get()==null) {
            return null;
        }
        return requestCacheThreadLocal.get().getTraceId();
    }
    public static String getToken(){
        if (requestCacheThreadLocal.get()==null) {
            return null;
        }
        return requestCacheThreadLocal.get().getToken();
    }
    public static String getUri(){
        if (requestCacheThreadLocal.get()==null) {
            return null; }
        return requestCacheThreadLocal.get().getUri();
    }


    public static void clearCache(){
        requestCacheThreadLocal.remove();
    }

}
