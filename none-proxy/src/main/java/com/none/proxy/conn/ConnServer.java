package com.none.proxy.conn;

import com.none.common.codec.PacketCodecHandler;
import com.none.proxy.cache.IMServerCache;
import com.none.proxy.handler.BackendHandler;
import com.none.proxy.router.RouterAlgorithm;
import com.none.proxy.router.round.RoundRobin;
import com.none.proxy.util.SpringBeanFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: zl
 * @Date: 2019/7/23 0:55
 */
@Slf4j
public class ConnServer {
    @Autowired
    private RouterAlgorithm routerAlgorithm = SpringBeanFactory.getBean(RoundRobin.class);

    @Autowired
    private IMServerCache cache = SpringBeanFactory.getBean(IMServerCache.class);

    public Channel connIMServer(String clientInfo) {
        //get server info
        String info = routerAlgorithm.selectServer(cache.getAllServers(), clientInfo);
        if (info == null || "".equals(info)) {
            log.error("接入IM服务异常：" + clientInfo);
            return null;
        }
        String[] infos = info.split(":");
        return connIMServer(infos[0], Integer.valueOf(infos[1]), clientInfo);
    }

    /**
     * 服务接入到指定IP，PORT
     *
     * @param ip
     * @param port
     * @return
     */
    public Channel connIMServer(String ip, int port, String clientInfo) {
        Bootstrap b = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.AUTO_READ, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(new BackendHandler());
                        p.addLast(PacketCodecHandler.INSTANCE);
                    }
                });
        ChannelFuture f = null;
        try {
            f = b.connect(ip, port).sync().addListener(future -> {
                if (future.isSuccess()) {
                    log.info(clientInfo + "已连接到im服务：【" + ip + ":" + port + "】");
                } else {
                    //TODO 这里设置重新分配服务的策略
                    log.error(clientInfo + "连接im服务：【" + ip + ":" + port + "】失败！");
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return f == null ? null : f.channel();
    }
}
