package com.none.proxy.router;

import java.util.List;

/**
 * @Author: zl
 * @Date: 2019/7/25 0:46
 */
public interface RouterAlgorithm {
    /**
     * 负载均衡算法，选择服务
     * @param values
     * @param key
     * @return
     */
    String selectServer(List<String> values, String key);
}
