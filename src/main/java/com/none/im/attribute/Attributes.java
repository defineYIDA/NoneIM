package com.none.im.attribute;

import io.netty.util.AttributeKey;

/**
 * @Author: zl
 * @Date: 2019/5/6 22:59
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
