package com.none.im.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zl
 * @Date: 2019/5/18 1:18
 */
@Data
@NoArgsConstructor
public class Session {
    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }
}
