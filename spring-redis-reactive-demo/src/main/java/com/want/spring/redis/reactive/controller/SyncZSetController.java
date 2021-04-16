package com.want.spring.redis.reactive.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author WangZhiJian
 * @since 2021/4/16
 */
@Slf4j
@RestController
@Api(tags = "sync 操作 redis zset")
public class SyncZSetController  { //implements SmartInitializingSingleton
    private final String RESP_STR = "success";
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @ApiOperation(value = "sync add key")
    @PutMapping("sync/zset/{rkey}/{key}/{value}")
    public String add(@PathVariable("rkey")String rkey,@PathVariable("key")String key , @PathVariable("value")double value){
        redisTemplate.opsForZSet().add(rkey,key,value);
        return RESP_STR;
    }
    @ApiOperation(value = "sync unionAndStore key")
    @GetMapping("sync/zset/unionAndStore/{rkeys}/{len}")
    public Set<String> unionAndStore(@PathVariable("rkeys")String rkeys, @PathVariable("len")Long len ){
        String[] keys = rkeys.split(",");
        List<String> otherKeys = Stream.of(keys)
                .skip(1)
                .collect(Collectors.toList());
        redisTemplate.opsForZSet().unionAndStore(keys[0],otherKeys,"unionKey");
        return redisTemplate.opsForZSet().range("unionKey",1L,len);
    }
    @ApiOperation(value = "sync range key")
    @GetMapping("sync/zset/range/{rkey}/{len}")
    public Set<String> range(@PathVariable("rkey")String rkey,@PathVariable("len")Long len ){
        return redisTemplate.opsForZSet().range(rkey, 1L, len);
    }

//    @Override
//    public void afterSingletonsInstantiated() {
//        String[] keys = new String[]{"key1","key2"};
//
//        Random r = new Random();
//
//        Flux.range(0,50)
//                .subscribe(l -> {
//                    int rkey = r.nextInt(2);
//                    int key = r.nextInt(50);
//                    double value = Math.random() * 100;
//                    redisTemplate.opsForZSet().add(keys[rkey],key+"",value);
//                });
//
//    }

    @PreDestroy
    public void preDestroy(){
        log.info("deleted...");
        redisTemplate.delete("key1");
        redisTemplate.delete("key2");
    }
}
