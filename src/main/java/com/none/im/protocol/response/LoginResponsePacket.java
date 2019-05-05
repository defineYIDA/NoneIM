package com.none.im.protocol.response;

import com.none.im.protocol.Packet;
import lombok.Data;

import static com.none.im.protocol.command.Command.LOGIN_RESPONSE;

/**
 * @Author: zl
 * @Date: 2019/5/5 1:05
 */
@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
