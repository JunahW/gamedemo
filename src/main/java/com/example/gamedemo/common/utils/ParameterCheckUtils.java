package com.example.gamedemo.common.utils;

import com.example.gamedemo.common.constant.SystemConstant;

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
     * @param clazz
     * @return
     */
    public static boolean checkParams(String msg, Class clazz) {
        boolean flag = true;
        int paramLength = clazz.getDeclaredFields().length;
        if (null == msg || msg.split(SystemConstant.SPLIT_TOKEN).length != paramLength + 1) {
            flag = false;
        }
        return flag;
    }
}
