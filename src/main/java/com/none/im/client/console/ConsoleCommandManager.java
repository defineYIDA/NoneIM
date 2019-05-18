package com.none.im.client.console;

import com.none.im.util.SessionUtil;
import io.netty.channel.Channel;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author: zl
 * @Date: 2019/5/19 0:02
 */
public class ConsoleCommandManager implements ConsoleCommand {
    //控制台指令集
    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        String command = scanner.next();

        if (!SessionUtil.hasLogin(channel)) {
            return;
        }
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.out.println("无法识别[" + command + "]指令，请重新输入！");
        }
    }
}
