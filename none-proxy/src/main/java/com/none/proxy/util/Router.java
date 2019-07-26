package com.none.proxy.util;

import io.netty.channel.Channel;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来处理client，proxy，server间路由(转发)关系
 *
 * @Author: zl
 * @Date: 2019/7/24 0:26
 */
public class Router {
    //转发关系：client->server
    private static final Map<Channel, Channel> forwordToServerMap = new ConcurrentHashMap<>();

    //转发关系：server->client
    private static final Map<Channel, Channel> forwordToClientMap = new ConcurrentHashMap<>();

    public static void bindForwordInfo(Channel client, Channel server) {
        forwordToServerMap.put(client, server);
        forwordToClientMap.put(server, client);
    }

    public static Channel getClientChannel(Channel k) {
        return forwordToClientMap.get(k);
    }

    public static Channel getServerChannel(Channel k) {
        return forwordToServerMap.get(k);
    }

    /**
     * 泛洪
     * @param msg
     */
    public static void flooding(Object msg) {
        Iterator<Channel> iterator = forwordToServerMap.values().iterator();
        while (iterator.hasNext()) {
            Channel channel = iterator.next();
            channel.writeAndFlush(msg);
        }
    }
}
