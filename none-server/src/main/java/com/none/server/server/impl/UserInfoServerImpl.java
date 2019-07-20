package com.none.server.server.impl;

import com.none.common.Const;
import com.none.common.util.JSONUtil;
import com.none.server.server.UserInfoServer;
import com.none.server.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zl
 * @Date: 2019/7/17 20:48
 */
@Service
@Slf4j
public class UserInfoServerImpl implements UserInfoServer {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean saveUserLoginStatus(String userName, String sessionID) {
        String key = Const.LOGIN_STATUS_PREFIX + userName;
        if (checkUserStatus(userName)) {
            log.error(new Date() + ": 注册用户[{}]登陆信息失败！", userName);
            return false;
        } else {
            redisTemplate.opsForValue().set(key, sessionID);
            log.info(new Date() + "登陆信息:[K: " + key + ", V: " + sessionID + "] 注册到redis成功!");
            return true;
        }
    }

    @Override
    public boolean checkUserStatus(String userName) {
        return getSessionID(userName) != null;
    }

    @Override
    public String getSessionID(String userName) {
        return redisTemplate.opsForValue().get(Const.LOGIN_STATUS_PREFIX + userName);
    }

    @Override
    public void removeLoginStatus(String userName) {
        redisTemplate.delete(Const.LOGIN_STATUS_PREFIX + userName);
    }

    @Override
    public boolean saveRouterInfo(String userName, String sessionID) throws Exception{
        String ip = InetAddress.getLocalHost().getHostAddress();
        String key = Const.ROUTER_PREFIX + userName;
        Map<String, String> map = new HashMap<>();
        String value = JSONUtil.setReouterInfo(sessionID, ip, appConfig.getWebServerPort() + "", appConfig.getImServerPort() + "");
        //判断client是否注册信息
        if (getRouterInfo(userName) != null) {
            return false;
        } else {
            redisTemplate.opsForValue().set(key, value);
            log.info(new Date() + "路由信息:[K: " + key + ", V: " + value + "] 注册到redis成功!");
            return true;
        }
    }

    @Override
    public String getRouterInfo(String userName) {
        return redisTemplate.opsForValue().get(Const.ROUTER_PREFIX + userName);
    }

    @Override
    public void removeRouterInfo(String userName) {
        redisTemplate.delete(Const.ROUTER_PREFIX + userName);
    }

    @Override
    public void removeAllInfoInRedis(String userName) {
        removeLoginStatus(userName);
        removeRouterInfo(userName);
    }
}
