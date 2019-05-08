package com.example.gamedemo.server.common.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author: wengj
 * @date: 2019/5/7
 * @description: 请求和处理方法映射表
 */
public class ControllerManager {
    private static final Logger logger = LoggerFactory.getLogger(ControllerManager.class);


    /**
     * 请求和处理之间的映射
     */
    private final static Map<String, InvokeMethod> CONTROLLER_MAP = new ConcurrentHashMap<>();

    /**
     * @param cmd
     * @param invokeMethod
     */
    public static void add(String cmd, InvokeMethod invokeMethod) {
        if (CONTROLLER_MAP.containsKey(cmd)) {
            logger.error("请求指令{}重复", cmd);
            throw new RuntimeException();
        } else {
            CONTROLLER_MAP.put(cmd, invokeMethod);
        }
    }

    /**
     * 获取指令的处理方法
     *
     * @param cmd
     * @return
     */
    public static InvokeMethod get(String cmd) {
        // 通过int的msgId找到枚举的MsgId
        return CONTROLLER_MAP.get(cmd);
    }
}
