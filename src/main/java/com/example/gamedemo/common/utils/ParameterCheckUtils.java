package com.example.gamedemo.common.utils;

import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.dispatcher.ControllerManager;

/**
 * @author: wengj
 * @date: 2019/5/9
 * @description: 参数检查工具类
 */
public class ParameterCheckUtils {
    /**
     * 检查参数的长度是否合法
     *
     * @param msg
     * @return
     */
    public static boolean checkParams(String msg) {
        boolean flag = true;
        Class clazz = ControllerManager.getClassByCmd(msg.split(SystemConstant.SPLIT_TOKEN)[0]);
        int paramLength = clazz.getDeclaredFields().length;
        if (null == msg || msg.split(SystemConstant.SPLIT_TOKEN).length != paramLength + 1) {
            flag = false;
        }
        return flag;
    }

    /**
     * 检查指令是否合法
     *
     * @param msg
     * @return
     */
    public static boolean checkCmd(String msg) {
        boolean flag = true;
        Class clazz = ControllerManager.getClassByCmd(msg.split(SystemConstant.SPLIT_TOKEN)[0]);
        if (clazz == null) {
            flag = false;
        }
        return flag;
    }
}
