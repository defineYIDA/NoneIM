package com.none.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zl
 * @Date: 2019/5/21 22:50
 */
public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler() {
        //arg1:读的空闲时间，arg2：写的空闲时间 arg3：读写的空闲时间，arg4：单位
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
