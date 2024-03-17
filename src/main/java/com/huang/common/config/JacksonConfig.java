package com.huang.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Classname JacksonConfig
 * @CreatedDate 2024/3/13 15:26
 * @Author Huang
 */
@Configuration
public class JacksonConfig {

    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

//    @Configuration
//    public class LocalDateConverter implements Converter<String,LocalDate>{
//
//        /**
//         * Convert the source object of type {@code S} to target type {@code T}.
//         *
//         * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
//         * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
//         * @throws IllegalArgumentException if the source cannot be converted to the desired target type
//         */
//        @Override
//        public LocalDate convert(String source) {
//            return LocalDate.parse(source, dateFormatter);
//        }
//    }
//    @Configuration
//    public class LocalDateTimeConverter implements Converter<String, LocalDateTime>{
//
//        /**
//         * Convert the source object of type {@code S} to target type {@code T}.
//         *
//         * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
//         * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
//         * @throws IllegalArgumentException if the source cannot be converted to the desired target type
//         */
//        @Override
//        public LocalDateTime convert(String source) {
//            return LocalDateTime.parse(source, dateTimeFormatter);
//        }
//    }
    @Bean
    public ObjectMapper objectMapper(){
        return staticObjectMapper();
    }

    public static ObjectMapper staticObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(dateFormatter));
        javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalTime.class,new LocalTimeDeserializer(timeFormatter));

        //Date序列化和反序列化
        javaTimeModule.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = formatter.format(date);
                jsonGenerator.writeString(formattedDate);
            }
        });
        javaTimeModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = jsonParser.getText();
                try {
                    return format.parse(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}
