package com.xye8.roc.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisTemplate 配置
 *
 */
@Configuration
public class RedisSerializerConfig {



    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {


        return new GenericFastJsonRedisSerializer();
    }
}
