package com.none.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: zl
 * @Date: 2019/7/15 22:11
 */
@Component
public class AppConfig {
    @Value("${server.port}")
    private int webServerPort;

    @Value("${im.server.port}")
    private int imServerPort;

    @Value("${zk.addr}")
    private String zkAddr;

    @Value("${zk.connect.timeout}")
    private int zkConnectTimeout;

    @Value("${zk.root}")
    private String zkRoot;

    @Value("${zk.switch}")
    private boolean zkSwitch;

    public int getWebServerPort() {
        return webServerPort;
    }

    public void setWebServerPort(int webServerPort) {
        this.webServerPort = webServerPort;
    }

    public int getImServerPort() {
        return imServerPort;
    }

    public void setImServerPort(int imServerPort) {
        this.imServerPort = imServerPort;
    }

    public String getZkAddr() {
        return zkAddr;
    }

    public void setZkAddr(String zkAddr) {
        this.zkAddr = zkAddr;
    }

    public int getZkConnectTimeout() {
        return zkConnectTimeout;
    }

    public void setZkConnectTimeout(int zkConnectTimeout) {
        this.zkConnectTimeout = zkConnectTimeout;
    }

    public String getZkRoot() {
        return zkRoot;
    }

    public void setZkRoot(String zkRoot) {
        this.zkRoot = zkRoot;
    }

    public boolean isZkSwitch() {
        return zkSwitch;
    }

    public void setZkSwitch(boolean zkSwitch) {
        this.zkSwitch = zkSwitch;
    }
}
