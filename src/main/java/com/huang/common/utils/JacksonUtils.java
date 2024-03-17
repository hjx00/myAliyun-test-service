package com.huang.common.utils;

import com.huang.common.config.JacksonConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date 2022/5/6 14:14
 */
public class JacksonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private static final ObjectMapper objectMapper = JacksonConfig.staticObjectMapper();

    public static <T> String toJson(T obj) {
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.warn("toJSON error！",e);
        }

        return jsonStr;
    }

    public static <T> T fromByte(byte[] bytes, Class<T> clazz){
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            logger.warn("toBean error！",e);
        }

        return null;
    }

    public static <T> T fromJson(String json, Class<T> type) {
        T obj = null;
        try {
            obj = objectMapper.readValue(json,type);
        } catch (Exception e) {
            logger.warn("fromJSON error！",e);
        }

        return obj;
    }

    public static <T> T fromJson(String json, TypeReference<T> type) {
        T obj = null;
        try {
            obj = objectMapper.readValue(json,type);
        } catch (Exception e) {
            logger.warn("fromJSON error！",e);
        }

        return obj;
    }

    public static <T> List<T> toList(String json){
        try {
            return objectMapper.readValue(json, new TypeReference<List<T>>(){});
        } catch (IOException e) {
            logger.warn("json to list error.", e);
        }

        return Collections.emptyList();
    }

    public static <T> List<T> toList(byte[] bytes){
        try {
            return objectMapper.readValue(bytes, new TypeReference<List<T>>(){});
        } catch (IOException e) {
            logger.warn("json to list error.", e);
        }

        return Collections.emptyList();
    }


    public static <T> Map toMap(String json){
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            logger.warn("json to list error.", e);
        }

        return Collections.emptyMap();
    }

    public static byte[] toByteArray(Object t){
        if (t == null) {
            return new byte[0];
        }

        try {
            return objectMapper.writeValueAsBytes(t);
        } catch (Exception ex) {
            throw new RuntimeException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }
}
