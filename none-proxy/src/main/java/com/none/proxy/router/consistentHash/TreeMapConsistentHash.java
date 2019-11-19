package com.none.proxy.router.consistentHash;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author: zl
 * @Date: 2019/8/2 11:18
 */
public class TreeMapConsistentHash extends AbstractConsistentHash {

    private TreeMap<Long, String> treeMap = new TreeMap<>();

    /**
     * 虚拟节点数
     */
    private static final int VIRTUAL_NODE_SIZE = 4;

    @Override
    protected void add(long key, String value) {
        //添加虚拟节点
        for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
            Long hash = hash("vir" + key + i);
            treeMap.put(hash, value);
        }
    }

    @Override
    protected void sort() {
        super.sort();
    }

    @Override
    protected String getFirstNodeValue(String value) {
        long hash = super.hash(value);
        System.out.println("value=" + value + " hash = " + hash);

        //此映射的部分视图，其键大于（或等于，如果 inclusive 为 true）fromkey
        SortedMap<Long, String> last = treeMap.tailMap(hash);
        String virtualNode;
        if (!last.isEmpty()) {
            virtualNode = last.get(last.firstKey());
        } else {
            virtualNode = treeMap.firstEntry().getValue();
        }
        return virtualNode.substring(0, virtualNode.indexOf("vir"));
    }

    @Override
    public String process(List<String> values, String key) {
        return super.process(values, key);
    }

    @Override
    public Long hash(String value) {
        return super.hash(value);
    }
}
