package com.none.server.Server.impl;

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
        if (checkUserStatus(userName)) {
            return false;
        } else {
            redisTemplate.opsForValue().set(userName, sessionID);
            System.out.println(new Date() + ":[K: " + userName + ", V: " + sessionID + "] 注册到redis成功!");
            return true;
        }
    }

    @Override
    public boolean checkUserStatus(String userName) {
        return getSessionID(userName) != null;
    }

    @Override
    public String getSessionID(String userName) {
        return redisTemplate.opsForValue().get(userName);
    }
}
