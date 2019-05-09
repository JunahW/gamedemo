package com.example.gamedemo.server.common.utils;

import com.example.gamedemo.server.common.session.SessionManager;
import com.example.gamedemo.server.common.session.TSession;

/**
 * @author: wengj
 * @date: 2019/5/9
 * @description: 参数检查工具类
 */
public class ParameterCheckUtils {
    /**
     * 检查参数的长度是否合法
     *
     * @param session
     * @param msg
     * @param paramLength
     * @return
     */
    public static boolean checkParams(TSession session, String msg, int paramLength) {
        boolean flag = true;
        if (null == msg || msg.length() < paramLength) {
            flag = false;
            SessionManager.sendMessage(session, "请求参数有误\r\n");
        }
        return flag;
    }
}
