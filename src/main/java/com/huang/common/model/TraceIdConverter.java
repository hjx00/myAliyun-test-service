package com.huang.common.model;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.huang.common.utils.RequestTraceUtil;

import java.util.UUID;

/**
 * @Classname TraceIdConverter
 * @CreatedDate 2024/3/14 16:13
 * @Author Huang
 */
public class TraceIdConverter extends MessageConverter {
    @Override
    public String convert(ILoggingEvent event){
        String traceId = RequestTraceUtil.getTraceId();
        return traceId == null ? UUID.randomUUID().toString() : traceId;
    }
}
