package com.none.server.server;

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
     * 保存客户机和服务器的连接关系
     * @param userName
     * @param sessionID
     * @return
     */
    boolean saveRouterInfo(String userName, String sessionID) throws Exception;

    /**
     * 获取client的路由信息
     * @param userName
     * @return
     */
    String getRouterInfo(String userName);

    /**
     * 清除登陆信息
     * @param userName
     * @return
     */
    void removeLoginStatus(String userName);

    /**
     * 清除路由信息
     * @param userName
     */
    void removeRouterInfo(String userName);

    /**
     * 清除用户在redis中的所有信息
     * 登陆状态，路由
     * @param userName
     */
    void removeAllInfoInRedis(String userName);
}
