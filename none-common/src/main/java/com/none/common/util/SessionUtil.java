package com.none.common.util;

import com.none.common.attribute.Attributes;
import com.none.common.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

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

    private static final Map<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

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

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        channelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return channelGroupMap.get(groupId);
    }
}
