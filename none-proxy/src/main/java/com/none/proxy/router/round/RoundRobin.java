package com.none.proxy.router.round;

import com.none.proxy.router.RouterAlgorithm;

import java.rmi.ServerException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * RR，轮询
 *
 * @Author: zl
 * @Date: 2019/7/25 0:54
 */
public class RoundRobin implements RouterAlgorithm {
    private AtomicLong rotater = new AtomicLong(0);

    @Override
    public String selectServer(List<String> values, String key) {
        if (values.size() == 0) {
            throw new RuntimeException("无可用的IM服务");
        }
        Long idx = Math.abs(rotater.incrementAndGet()) % values.size();
        return values.get(idx.intValue());
    }
}
