package com.none.server.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: zl
 * @Date: 2019/7/15 22:57
 */
@Configuration
public class BeanConfig {
    @Autowired
    private AppConfig appConfig;

    /**
     * zk bean
     *
     * @return
     */
    @Bean
    public ZkClient createZKClientBean() {
        return new ZkClient(appConfig.getZkAddr(), appConfig.getZkConnectTimeout());
    }

    /**
     * redis bean
     *
     * @param factory
     * @return
     */
    @Primary
    @Bean
    public RedisTemplate<String, String> createRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
