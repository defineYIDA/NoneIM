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
    //<sessionID, Channel>
    private static final Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    private static final Map<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

    //<username, sessionID>映射关系和redis的缓存配合使用
    private static final Map<String, String> userSessionIDMap = new ConcurrentHashMap<>();

    private static void markAsLogin(Channel channel, Session session) {
        //注意这个参数的作用域，session域
        channel.attr(Attributes.SESSION).set(session);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static void bindSession(Session session, Channel channel) {
        channelMap.put(session.getSessionID(), channel);
        //这里思考对channel绑定参数的含义，对于原生的nio，对于长联的socket如果要设置session
        //那么肯定得基于sessionId+context域的Map<session>,而当前方式就简洁了许多
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            channelMap.remove(getSession(channel).getSessionID());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String sessionID) {
        return channelMap.get(sessionID);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        channelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return channelGroupMap.get(groupId);
    }
}
