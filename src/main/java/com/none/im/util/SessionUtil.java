package com.none.im.util;

import com.none.im.attribute.Attributes;
import com.none.im.session.Session;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zl
 * @Date: 2019/5/6 23:03
 */
public class SessionUtil {
    /**
     * 放这其实不太合适，该映射应该是context域
     */
    private static final Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    private static void markAsLogin(Channel channel, Session session) {
        //注意这个参数的作用域，session域
        channel.attr(Attributes.SESSION).set(session);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static void bindSession(Session session, Channel channel) {
        channelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            channelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return channelMap.get(userId);
    }
}
