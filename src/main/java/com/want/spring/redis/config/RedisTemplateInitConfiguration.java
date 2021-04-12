package com.want.spring.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import static io.lettuce.core.ReadFrom.REPLICA_PREFERRED;

/**
 * @author WangZhiJian
 * @since 2021/4/12
 */
@Configuration
public class RedisTemplateInitConfiguration {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(REPLICA_PREFERRED)
                .build();

        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration("10.1.25.28", 6379);
        serverConfig.setPassword("redis-pwd");
        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory redisConnectionFactory){
        return new StringRedisTemplate(redisConnectionFactory);
    }
}
