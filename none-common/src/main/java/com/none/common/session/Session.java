package com.none.common.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zl
 * @Date: 2019/5/18 1:18
 */
@Data
@NoArgsConstructor
public class Session {
    private String sessionID;
    private String userName;

    public Session(String sessionID, String userName) {
        this.sessionID = sessionID;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return sessionID + ":" + userName;
    }
}
