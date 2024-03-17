package com.huang.common.config;//package com.common.config;
//
//import ch.qos.logback.classic.AsyncAppender;
//import ch.qos.logback.classic.LoggerContext;
//
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import javax.annotation.PostConstruct;
//
//
///**
// * @Classname TheadPoolConfig
// * @CreatedDate 2024/3/14 16:59
// * @Author Huang
// */
//@Configuration
//public class TheadPoolConfig {
//
//    @PostConstruct
//    public void configLogExecutor(){
//        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
//        AsyncAppender asyncAppender = (AsyncAppender) context.getAppender("ASYNC");
//        if (asyncAppender != null) {
//            asyncAppender.setExecutor(CustomExecutor.getExecutor());
//            asyncAppender.start();
//        }
//    }
//}
