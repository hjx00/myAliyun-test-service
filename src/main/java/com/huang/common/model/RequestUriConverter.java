package com.huang.common.model;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.huang.common.utils.RequestTraceUtil;

import java.util.UUID;

/**
 * @Classname RequestUriConverter
 * @CreatedDate 2024/3/14 16:19
 * @Author Huang
 */
public class RequestUriConverter extends MessageConverter {
    @Override
    public String convert(ILoggingEvent event){
        String uri = RequestTraceUtil.getUri();
        return uri == null ? "" : uri;
    }
}
