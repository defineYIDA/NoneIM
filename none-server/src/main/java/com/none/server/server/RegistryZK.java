package com.none.server.server;

import com.none.server.config.AppConfig;
import com.none.server.util.SpringBeanFactory;
import com.none.server.util.zookeeper.ZKUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zl
 * @Date: 2019/7/15 22:53
 */
@Slf4j
public class RegistryZK implements Runnable {

    private ZKUtil zkUtil;

    private AppConfig appConfig;

    private String ip;

    private int imServerPort;

    private int serverPort;

    public RegistryZK(String ip, int imServerPort,int serverPort) {
        this.ip = ip;
        this.imServerPort = imServerPort;
        this.serverPort = serverPort;
        this.zkUtil = SpringBeanFactory.getBean(ZKUtil.class);
        this.appConfig = SpringBeanFactory.getBean(AppConfig.class);
    }

    @Override
    public void run() {
        if (appConfig.isZkSwitch()) {
            String path = appConfig.getZkRoot() + "/ip-" + ip + ":" + imServerPort + ":" + serverPort;
            zkUtil.createNode(path);
        }
    }
}
