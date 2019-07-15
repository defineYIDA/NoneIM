package com.none.server;

import com.none.server.config.AppConfig;
import com.none.server.util.zookeeper.RegistryZK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;


/**
 * @Author: zl
 * @Date: 2019/7/15 20:51
 */
@SpringBootApplication
@Slf4j
public class ServerApplication implements CommandLineRunner {

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        log.info("none-server init");
    }

    @Override
    public void run(String... strings) throws Exception {
        String ip = InetAddress.getLocalHost().getHostAddress();
        new Thread(() -> {
            new RegistryZK(ip, appConfig.getImServerPort(), appConfig.getWebServerPort());
        }
        ).start();
    }
}
