package com.none.proxy.util;

import com.alibaba.fastjson.JSON;
import com.none.proxy.cache.IMServerCache;
import com.none.proxy.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Autowired
    private IMServerCache serverCache;

    public void subscribeEvent() {
        zkClient.subscribeChildChanges(appConfig.getZkRoot(), (String parentPath, List<String> currentChilds) -> {
            log.info("更新im服务信息的本地缓存 parentPath=【{}】,currentChilds=【{}】", parentPath, currentChilds.toString());
            //更新im服务信息的本地缓存
            serverCache.updateCache(currentChilds);
        });
    }

    /**
     * 活动所有注册节点
     *
     * @return
     */
    public List<String> getAllNode() {
        List<String> children = zkClient.getChildren(appConfig.getZkRoot());
        log.info("查询所有服务节点成功=【{}】", JSON.toJSONString(children));
        return children;
    }
}
