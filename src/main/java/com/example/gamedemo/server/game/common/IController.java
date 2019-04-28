package com.example.gamedemo.server.game.common;


/**
 * @author juanh
 * @date ${DATE}
 * @description 控制器
 */
@FunctionalInterface
public interface IController {

    /**
     * 接收派发的数据，处理业务
     *
     * @param msg
     */
    Object handle(String msg);

}
