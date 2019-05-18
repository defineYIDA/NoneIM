package com.none.im.client.console;


import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Author: zl
 * @Date: 2019/5/19 0:00
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
