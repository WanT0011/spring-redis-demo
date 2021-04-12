package com.want.spring.redis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "增加key-value")
    @PostMapping("ops/{key}/{value}")
    public String addKey(@PathVariable("key")String key ,@PathVariable("value")String value){
        stringRedisTemplate.opsForValue().set(key,value);
        return RESP_STR;
    }
    @ApiOperation(value = "删除key")
    @DeleteMapping("ops/{key}")
    public String removeKey(@PathVariable("key")String key){
        Boolean delete = stringRedisTemplate.delete(key);
        if(delete)
            return RESP_STR;
        return "key not present!";
    }
    @ApiOperation(value = "获取key")
    @GetMapping("ops/{key}")
    public String getKey(@PathVariable("key")String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
}
