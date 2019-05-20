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

    Byte LOGOUT_REQUEST = 5;

    Byte LOGOUT_RESPONSE = 6;

    Byte CREATE_GROUP_REQUEST = 7;

    Byte CREATE_GROUP_RESPONSE = 8;

    Byte LIST_GROUP_MEMBERS_REQUEST = 9;

    Byte LIST_GROUP_MEMBERS_RESPONSE = 10;

    Byte JOIN_GROUP_REQUEST = 11;

    Byte JOIN_GROUP_RESPONSE = 12;

    Byte QUIT_GROUP_REQUEST = 13;

    Byte QUIT_GROUP_RESPONSE = 14;

    Byte SHOW_CMD_REQUEST = 15;

    Byte SHOW_CMD_RESPONSE = 16;

    Byte GROUP_MESSAGE_REQUEST = 17;

    Byte GROUP_MESSAGE_RESPONSE = 18;
}
