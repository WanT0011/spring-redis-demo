package com.want.spring.redis.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author want
 * @createTime 2021.04.12.21:54
 */
@Slf4j
@SpringBootApplication
public class SpringRedisReactiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringRedisReactiveApplication.class,args);
        log.info("访问：http://localhost:8080/swagger-ui/index.html");
    }
}
