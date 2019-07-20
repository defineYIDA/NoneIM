package com.none.server.handler;

import com.none.common.protocol.request.MessageRequestPacket;
import com.none.common.protocol.response.MessageResponsePacket;
import com.none.common.session.Session;
import com.none.common.util.SessionUtil;
import com.none.server.server.UserInfoServer;
import com.none.server.server.impl.UserInfoServerImpl;
import com.none.server.util.SpringBeanFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zl
 * @Date: 2019/5/12 0:09
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private UserInfoServer userInfoServer = SpringBeanFactory.getBean(UserInfoServerImpl.class);

    //将可能的阻塞操作，放入线程池
    protected ThreadPoolExecutor pool;

    private MessageRequestHandler() {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Worker Pool-" + count++);
            }
        };
        this.pool = new ThreadPoolExecutor(100, 100, 1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        pool.submit(() -> {
            //获得session
            Session session = SessionUtil.getSession(ctx.channel());

            // 2.通过消息发送方的会话信息构造要发送的消息
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setFromSessionID(session.getSessionID());
            messageResponsePacket.setFromUserName(session.getUserName());
            messageResponsePacket.setMessage(messageRequestPacket.getMessage());

            //根据username拿到sessionid，这里可以考虑实现一个二级缓存，优先本地缓存查找，或者redis中查找
            String sessionID = userInfoServer.getSessionID(messageRequestPacket.getToUserName());
            //TODO  跨主机间的通信，拿到可能不在一台主机上的接收方channel

            Channel toChannel;
            if (sessionID == null || (toChannel = SessionUtil.getChannel(sessionID)) == null) {
                //回写到发送方
                messageResponsePacket.setMessage("[" + messageRequestPacket.getToUserName() + "] 不在线，发送失败!");
                messageResponsePacket.setFromSessionID("Server");
                messageResponsePacket.setFromUserName("Server");
                ctx.channel().writeAndFlush(messageResponsePacket);
            } else {
                // 4.将消息发送给消息接收方
                toChannel.writeAndFlush(messageResponsePacket);
            }
        });
    }
}
