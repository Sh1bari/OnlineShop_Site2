package com.example.onlineshop_site2.configs;

import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .indentOutput(true) // для удобства отладки
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")); // формат даты
    }
}
