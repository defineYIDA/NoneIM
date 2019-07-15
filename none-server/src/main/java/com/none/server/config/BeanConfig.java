package com.none.server.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zl
 * @Date: 2019/7/15 22:57
 */
@Configuration
public class BeanConfig {
    @Autowired
    private AppConfig appConfig;

    @Bean
    public ZkClient createZKClientBean() {
        return new ZkClient(appConfig.getZkAddr(), appConfig.getZkConnectTimeout());
    }
}
