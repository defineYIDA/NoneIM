package com.none.client.console;

import com.none.common.protocol.request.ShowCmdRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Author: zl
 * @Date: 2019/5/20 22:45
 */
public class ShowConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        ShowCmdRequestPacket packet = new ShowCmdRequestPacket();
        channel.writeAndFlush(packet);
    }
}
