package com.none.im.protocol.request;

import com.none.im.protocol.Packet;
import lombok.Data;

import static com.none.im.protocol.command.Command.LOGIN_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/4 22:55
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
