package com.none.proxy.router.random;

import com.none.proxy.router.RouterAlgorithm;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: zl
 * @Date: 2019/7/25 0:41
 */
public class RandomHandle implements RouterAlgorithm {
    @Override
    public String selectServer(List<String> values, String key) {
        if (values.size() == 0) {
            throw new RuntimeException("无可用的IM服务");
        }
        return values.get(ThreadLocalRandom.current().nextInt(values.size()));
    }
}
