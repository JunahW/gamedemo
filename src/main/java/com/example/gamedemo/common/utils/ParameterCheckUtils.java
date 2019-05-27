package com.example.gamedemo.common.utils;

import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;

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
     * @param clazz
     * @return
     */
    public static boolean checkParams(TSession session, String msg, Class clazz) {
        boolean flag = true;
        int paramLength = clazz.getDeclaredFields().length;
        if (null == msg || msg.split(SystemConstant.SPLIT_TOKEN).length != paramLength + 1) {
            flag = false;
            SessionManager.sendMessage(session, "请求参数有误\r\n");
        }
        return flag;
    }
}
