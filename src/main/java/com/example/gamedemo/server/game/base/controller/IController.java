package com.example.gamedemo.server.game.base.controller;


import io.netty.channel.ChannelHandlerContext;

/**
 * @author: wengj
 * @date: 2019/5/6
 * @description: 控制器
 */
@FunctionalInterface
public interface IController {

    /**
     * 接收派发的数据，处理业务
     *
     * @param cxt
     * @param msg
     * @return
     */
    Object handle(ChannelHandlerContext cxt, String msg);

}
