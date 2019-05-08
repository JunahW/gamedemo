package com.example.gamedemo.server.common.dispatcher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author: wengj
 * @date: 2019/5/7
 * @description: 请求和处理方法映射表
 */
public class ControllerManager {


    /**
     * MsgId标志和服务之间的映射
     */
    private final static Map<String, InvokeMethod> CONTROLLER_MAP = new ConcurrentHashMap<>();

    public static void add(String cmd, InvokeMethod invokeMethod) {
        CONTROLLER_MAP.put(cmd, invokeMethod);
    }


    public static InvokeMethod get(String cmd) {
        // 通过int的msgId找到枚举的MsgId
        return CONTROLLER_MAP.get(cmd);
    }
}
