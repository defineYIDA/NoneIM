package com.none.common.util;

import java.util.UUID;

/**
 * @Author: zl
 * @Date: 2019/5/19 1:18
 */
public class IDUtil {
    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
