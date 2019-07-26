package com.none.proxy.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.none.proxy.router.RouterAlgorithm;
import com.none.proxy.router.round.RoundRobin;
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
 * @Date: 2019/7/21 23:38
 */
@Configuration
public class BeanConfig {
    @Autowired
    private AppConfig appConfig;

    @Bean
    public ZkClient createZKClientBean() {
        return new ZkClient(appConfig.getZkAddr(), appConfig.getZkConnectTimeout());
    }

    @Bean
    public LoadingCache<String, String> createCache() {
        return
                CacheBuilder
                        .newBuilder()
                        .build(new CacheLoader<String, String>() {
                            @Override
                            public String load(String s) throws Exception {
                                return null;
                            }
                        });
    }

    @Primary
    @Bean
    public RedisTemplate<String, String> createRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RouterAlgorithm createRouterAlgorithm() {
        //TODO 一致性hash算法
        return new RoundRobin();
    }

}
