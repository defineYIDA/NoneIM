package com.none.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: zl
 * @Date: 2019/7/20 22:55
 */
@Slf4j
@SpringBootApplication
public class ProxyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
        log.info("none-proxy init");
    }

    @Override
    public void run(String... strings) throws Exception {

    }

}
