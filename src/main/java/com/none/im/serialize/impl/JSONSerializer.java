package com.none.im.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.none.im.serialize.Serializer;
import com.none.im.serialize.SerializerAlgorithm;

/**
 * @Author: zl
 * @Date: 2019/5/4 23:12
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
