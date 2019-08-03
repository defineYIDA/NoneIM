package com.none.proxy.router.consistentHash;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * 一致性hash
 *
 * @Author: zl
 * @Date: 2019/8/2 11:04
 */
public abstract class AbstractConsistentHash {

    /**
     * 新增节点
     *
     * @param key
     * @param value
     */
    protected abstract void add(long key, String value);

    /**
     * 排序节点
     */
    protected void sort() {
    }

    /**
     * 根据当前的key通过一致性hash算法取出一个节点
     *
     * @param value
     * @return
     */
    protected abstract String getFirstNodeValue(String value);

    /**
     * 传入节点列表以及客户端的信息获取一个服务节点
     *
     * @param values
     * @param key
     * @return
     */
    public String process(List<String> values, String key) {

        for (String value : values) {
            add(hash(value), value);
        }
        sort();
        return getFirstNodeValue(key);
    }

    /**
     * hash 运算
     *CRC32_HASH、FNV1_32_HASH、KETAMA_HASH
     * @param value
     * @return
     */
    public Long hash(String value) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = value.getBytes(StandardCharsets.UTF_8);

        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // hash code, Truncate to 32-bits
        long hashCode = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);

        return hashCode & 0xffffffffL;
    }

}
