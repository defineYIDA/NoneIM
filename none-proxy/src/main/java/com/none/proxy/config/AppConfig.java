package com.none.proxy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: zl
 * @Date: 2019/7/21 23:48
 */
@Component
public class AppConfig {

    @Value("${proxy.port}")
    private int proxyPort;

    @Value("${zk.addr}")
    private String zkAddr;

    @Value("${zk.root}")
    private String zkRoot;

    @Value("${zk.connect.timeout}")
    private int zkConnectTimeout;

    //路由算法
    @Value("${route.algorithm}")
    private String routeAlgorithm;

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getZkAddr() {
        return zkAddr;
    }

    public void setZkAddr(String zkAddr) {
        this.zkAddr = zkAddr;
    }

    public String getZkRoot() {
        return zkRoot;
    }

    public void setZkRoot(String zkRoot) {
        this.zkRoot = zkRoot;
    }

    public int getZkConnectTimeout() {
        return zkConnectTimeout;
    }

    public void setZkConnectTimeout(int zkConnectTimeout) {
        this.zkConnectTimeout = zkConnectTimeout;
    }

    public String getRouteAlgorithm() {
        return routeAlgorithm;
    }

    public void setRouteAlgorithm(String routeAlgorithm) {
        this.routeAlgorithm = routeAlgorithm;
    }
}
