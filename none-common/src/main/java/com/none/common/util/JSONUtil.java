package com.none.common.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: zl
 * @Date: 2019/7/19 21:17
 */
public class JSONUtil {

    /**
     * 获得路由信息在redis里存储的json字符串
     * @param sessionID
     * @param ip
     * @param imPort
     * @param webPort
     * @return
     */
    public static String setReouterInfo (String sessionID, String ip, String webPort, String imPort) {
        JSONObject json = new JSONObject();
        json.put("id", sessionID);
        json.put("ip", ip);
        json.put("webPort", webPort);
        json.put("imPort", imPort);
        return json.toString();
    }
}
