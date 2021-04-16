package com.want.spring.redis.reactive.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author WangZhiJian
 * @since 2021/4/16
 */
@Slf4j
@RestController
@Api(tags = "操作 redis zset")
public class ZSetController implements SmartInitializingSingleton {
    private final String RESP_STR = "success";
    @Resource
    private ReactiveZSetOperations<String,String> zSetOperations;

    @ApiOperation(value = "add key")
    @PutMapping("zset/{rkey}/{key}/{value}")
    public Mono<String> add(@PathVariable("rkey")String rkey, @PathVariable("key")String key , @PathVariable("value")double value){
        return zSetOperations.add(rkey,key,value)
                .map(f -> RESP_STR);
    }
    @ApiOperation(value = "unionAndStore key")
    @GetMapping("zset/unionAndStore/{rkeys}/{len}")
    public Mono<List<String>> unionAndStore(@PathVariable("rkeys")String rkeys,@PathVariable("len")Long len ){
        String[] keys = rkeys.split(",");
        List<String> otherKeys = Stream.of(keys)
                .skip(1)
                .collect(Collectors.toList());
        return zSetOperations.unionAndStore(keys[0],otherKeys,"unionKey")
                .flatMapMany(l -> zSetOperations.range("unionKey", Range.closed(1L,len)))
                .collectList();
    }
    @ApiOperation(value = "range key")
    @GetMapping("zset/range/{rkey}/{len}")
    public Mono<List<String>> range(@PathVariable("rkey")String rkey,@PathVariable("len")Long len ){
        return zSetOperations.range(rkey,Range.closed(1L,len))
                .collectList();
    }


    @Override
    public void afterSingletonsInstantiated() {
        String[] keys = new String[]{"key1","key2"};

        Random r = new Random();

        Flux.range(0,50)
                .subscribe(l -> {
                    int rkey = r.nextInt(2);
                    int key = r.nextInt(50);
                    double value = Math.random() * 100;
                    zSetOperations.add(keys[rkey],key+"",value)
                            .subscribe();
                });

    }
}
