package com.want.spring.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author WangZhiJian
 * @since 2021/4/11
 */
@EnableOpenApi
@SpringBootApplication
public class SpringRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class,args);
    }

}
