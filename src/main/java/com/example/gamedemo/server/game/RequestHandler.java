package com.example.gamedemo.server.game;

import com.example.gamedemo.server.game.base.controller.IController;
import com.example.gamedemo.server.game.manager.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description 请求分发
 * @date 2019/5/7
 */
public class RequestHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.info("===>receive msg:" + msg);
        //执行请求分发
        //根据指令获取当前的指令多对应的controller
        IController controller = ControllerManager.get(msg.split(" ")[0]);
        //String returnMsg = "指令有误";
        if (controller != null) {
            ControllerManager.execute(controller, ctx, msg);
        }
        //ctx.channel().writeAndFlush("server return msg:" + returnMsg + "\r\n");
    }


}
