package com.none.server.util.zookeeper;

import com.none.server.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: zl
 * @Date: 2019/7/15 22:49
 */
@Component
@Slf4j
public class ZKUtil {
    @Autowired
    private ZkClient zkClient;

    @Autowired
    private AppConfig appConfig;

    /**
     * 创建子节点
     * @param path
     */
    public void createNode(String path) {
        createRootNode();
        zkClient.createEphemeral(path);
        log.info("注册 zookeeper 成功，msg=[{}]", path);
    }

    /**
     * 创建根节点
     */
    private void createRootNode() {
        //判断是否存在root节点
        if (!zkClient.exists(appConfig.getZkRoot())) {
            //创建root
            zkClient.createPersistent(appConfig.getZkRoot());
        }
    }
}
