package com.none.common.protocol.request;


import com.none.common.protocol.Packet;
import lombok.Data;

import static com.none.common.protocol.command.Command.LOGIN_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/4 22:55
 */
@Data
public class LoginRequestPacket extends Packet {
    private String sessionID;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
