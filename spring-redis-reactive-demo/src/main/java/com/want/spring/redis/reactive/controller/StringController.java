package com.want.spring.redis.reactive.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author WangZhiJian
 * @since 2021/4/12
 */
@RestController
@Api(tags = "操作 redis string")
public class StringController {

    private final String RESP_STR = "success";

    @Resource
    private ReactiveRedisOperations<String,String> reactiveRedisTemplate;

    @ApiOperation(value = "增加key-value")
    @PostMapping("str/{key}/{value}")
    public Mono<String> addKey(@PathVariable("key")String key ,@PathVariable("value")String value){
        return reactiveRedisTemplate.opsForValue().append(key,value)
                .map(l -> RESP_STR);
    }
    @ApiOperation(value = "删除key")
    @DeleteMapping("str/{key}")
    public Mono<String> removeKey(@PathVariable("key")String key){
        return reactiveRedisTemplate.delete(key)
                .map(flag -> flag > 0 ? RESP_STR:"key not present!");
    }
    @ApiOperation(value = "获取key")
    @GetMapping("str/{key}")
    public Mono<String> getKey(@PathVariable("key")String key){
        return reactiveRedisTemplate.opsForValue().get(key);
    }
}
