package com.none.common.protocol;

import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(deserialize = false,serialize = false)
    private Byte version = 1;

    /**
     * 获得指令
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
