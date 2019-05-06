package com.example.gamedemo.server.game.manager;

import com.example.gamedemo.server.game.base.controller.IController;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author juanh
 * @date ${DATE}
 * @description
 */

@Component
public class ControllerManager {


    /**
     * MsgId标志和服务之间的映射
     */
    private final static Map<String, IController> CONTROLLER_MAP = new ConcurrentHashMap<>();

    public static void add(String cmd, IController controller) {
        CONTROLLER_MAP.put(cmd, controller);
    }


    public IController get(String cmd) {
        // 通过int的msgId找到枚举的MsgId
        return CONTROLLER_MAP.get(cmd);
    }

    /**
     * @param controller 要执行的任务
     * @param msg        信息
     */
    public Object execute(IController controller, ChannelHandlerContext cxt, String msg) {
        return controller.handle(cxt, msg);
    }


}
