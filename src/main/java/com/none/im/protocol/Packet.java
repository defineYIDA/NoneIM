package com.none.im.protocol;

import com.none.im.protocol.command.Command;
import lombok.Data;

/**
 * @Author: zl
 * @Date: 2019/5/4 22:19
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 获得指令
     * @return
     */
    public abstract Byte getCommand();

}
