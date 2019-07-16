package com.none.common.attribute;

import com.none.common.session.Session;
import io.netty.util.AttributeKey;

/**
 * @Author: zl
 * @Date: 2019/5/6 22:59
 */
public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
