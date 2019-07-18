package com.none.server.Server;

/**
 * @Author: zl
 * @Date: 2019/7/17 20:48
 */
public interface UserInfoServer {
    /**
     * 将用户登陆状态上传到redis
     *
     * @param userName
     * @param sessionID
     * @return
     */
    boolean saveUserLoginStatus(String userName, String sessionID);

    /**
     * 判断用户登陆状态
     *
     * @param userName
     * @return
     */
    boolean checkUserStatus(String userName);

    /**
     * 获得SessionID，根据userName
     *
     * @param userName
     * @return
     */
    String getSessionID(String userName);

    /**
     * 清除登陆信息
     * @param userName
     * @return
     */
    void removeLoginStatus(String userName, String sessionID);
}
