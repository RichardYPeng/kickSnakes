package com.kob.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig { // 当需要用到一个实例的时候就需要加入一个config并且返回一个实例就可以了
    @Bean
    public RestTemplate getRestTemplate() { // 在两个进程间进行通信；
        return new RestTemplate();
    }
}
