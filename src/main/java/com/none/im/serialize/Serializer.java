package com.none.im.serialize;

import com.none.im.serialize.impl.JSONSerializer;

/**
 * @Author: zl
 * @Date: 2019/5/4 23:06
 * 序列化接口
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();
    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     * 编码
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     * 解码
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
