package com.bzvs.easydict.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collections;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor authTokenInterceptor() {
        return template -> template.header("Authorization", "Token ec04b7f7a75348d2237f17272bf2d6667f272781");
    }

    @Bean
    public HttpMessageConverter<Object> textMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        return converter;
    }

}
