package com.none.server.Server.impl;

import com.none.common.Const;
import com.none.server.Server.UserInfoServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: zl
 * @Date: 2019/7/17 20:48
 */
@Service
@Slf4j
public class UserInfoServerImpl implements UserInfoServer {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean saveUserLoginStatus(String userName, String sessionID) {
        String key = Const.LOGIN_STATUS_PREFIX + userName;
        if (checkUserStatus(userName)) {
            log.error(new Date() + ": 注册用户[{}]登陆信息失败！",userName);
            return false;
        } else {
            redisTemplate.opsForValue().set(key, sessionID);
            log.info(new Date() + ":[K: " + key + ", V: " + sessionID + "] 注册到redis成功!");
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
    public void removeLoginStatus(String userName, String sessionID) {
        redisTemplate.delete(Const.LOGIN_STATUS_PREFIX + userName);
    }
}
