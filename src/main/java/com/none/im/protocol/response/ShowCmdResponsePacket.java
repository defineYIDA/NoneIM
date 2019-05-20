package com.none.im.protocol.response;

import com.none.im.protocol.Packet;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.none.im.protocol.command.Command.SHOW_CMD_RESPONSE;

/**
 * @Author: zl
 * @Date: 2019/5/20 22:51
 */
@Data
public class ShowCmdResponsePacket extends Packet {

    List<String> cmd = new ArrayList<>();

    @Override
    public Byte getCommand() {
        return SHOW_CMD_RESPONSE;
    }
}
