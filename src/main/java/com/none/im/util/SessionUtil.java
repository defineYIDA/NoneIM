package com.none.im.util;

import com.none.im.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @Author: zl
 * @Date: 2019/5/6 23:03
 */
public class SessionUtil {

    public static void markAsLogin(Channel channel) {
        //注意整个参数的作用域，类似于request域
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
