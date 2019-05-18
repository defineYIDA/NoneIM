package com.none.im.client.console;

import com.none.im.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Author: zl
 * @Date: 2019/5/19 0:14
 */
public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner sc, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.print("输入用户名登录: ");
        String username = sc.nextLine();
        loginRequestPacket.setUsername(username);

        // 密码使用默认的
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
