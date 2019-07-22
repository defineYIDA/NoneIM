package com.none.proxy.cache;

import com.google.common.cache.LoadingCache;
import com.none.proxy.util.ZKUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zl
 * @Date: 2019/7/22 0:42
 */
@Component
public class IMServerCache {
    @Autowired
    private LoadingCache<String, String> cache;

    @Autowired
    private ZKUtil zkUtil;

    private AtomicLong count = new AtomicLong();

    public void addCache(String key) {
        cache.put(key, key);
    }

    /**
     * 更新缓存
     * zk上服务的注册形式为：【"ip-" + ip + ":" + imServerPort + ":" + serverPort】
     *
     * @param currentServerList
     */
    public void updateCache(List<String> currentServerList) {
        cache.invalidateAll();
        for (String serverinfo : currentServerList) {
            String key = serverinfo.split("-")[1];
            addCache(key);
        }
    }

    public List<String> getAllServers() {
        List<String> list = new ArrayList<>();
        if (cache.size() == 0) {
            updateCache(zkUtil.getAllNode());
        }
        for (Map.Entry<String, String> entry : cache.asMap().entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }


}
