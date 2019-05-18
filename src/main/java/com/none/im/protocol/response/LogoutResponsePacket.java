package com.none.im.protocol.response;

import com.none.im.protocol.Packet;
import lombok.Data;

import static com.none.im.protocol.command.Command.LOGOUT_RESPONSE;

/**
 * @Author: zl
 * @Date: 2019/5/19 0:37
 */
@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
