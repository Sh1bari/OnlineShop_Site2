package com.example.onlineshop_site2.configs;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
                .indentOutput(true)
                .modulesToInstall(new JavaTimeModule()) // добавляем модуль для поддержки даты/времени
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")); // ваш текущий формат
    }
}
