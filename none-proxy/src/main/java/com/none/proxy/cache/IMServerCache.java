package com.none.proxy.cache;

import com.google.common.cache.LoadingCache;
import com.none.common.util.SessionUtil;
import com.none.proxy.handler.BackendHandler;
import com.none.proxy.util.ZKUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zl
 * @Date: 2019/7/22 0:42
 */
@Component
@Slf4j
public class IMServerCache {
    @Autowired
    private LoadingCache<String, String> cache;

    @Autowired
    private ZKUtil zkUtil;

    private AtomicLong count = new AtomicLong();

    public void addCache(String key) {
        cache.put(key, key);
    }

    /**
     * 更新缓存
     * zk上服务的注册形式为：【"ip-" + ip + ":" + imServerPort + ":" + serverPort】
     *
     * @param currentServerList
     */
    public void updateCache(List<String> currentServerList) {
        cache.invalidateAll();
        for (String serverinfo : currentServerList) {
            String key = serverinfo.split("-")[1];
            addCache(key);
            String[] infos = key.split(":");
            //connIMServer(infos[0], Integer.valueOf(infos[1]));
        }
    }

    public List<String> getAllServers() {
        List<String> list = new ArrayList<>();
        if (cache.size() == 0) {
            updateCache(zkUtil.getAllNode());
        }
        for (Map.Entry<String, String> entry : cache.asMap().entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    /*private Channel connIMServer(String ip, int port) {
        Bootstrap b = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.AUTO_READ, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();

                        //p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(new BackendHandler(), new LoggingHandler(LogLevel.INFO));
                    }
                });
        ChannelFuture f = b.connect(ip, port);
        log.info("已连接到im服务：【" + ip + ":" + port + "】");
        //Channel channel = f.channel();
        //SessionUtil.bindSession(ip + ":" + port, channel);
        return f.channel();
    }*/

}
