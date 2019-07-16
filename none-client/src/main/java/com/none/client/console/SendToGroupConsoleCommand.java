package com.none.client.console;

import com.none.common.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Author: zl
 * @Date: 2019/5/21 0:07
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个群组：");

        String toGroupId = scanner.next();
        System.out.print("发送消息：");
        String message = scanner.next();
        channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));

    }
}
