package com.none.im.protocol.command;

/**
 * @Author: zl
 * @Date: 2019/5/4 22:51
 * 命令标识
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;
}
