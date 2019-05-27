package com.example.gamedemo.server.game;

import com.example.gamedemo.server.common.constant.SessionAttributeKey;
import com.example.gamedemo.server.common.dispatcher.ControllerManager;
import com.example.gamedemo.server.common.dispatcher.InvokeMethod;
import com.example.gamedemo.server.common.session.TSession;
import com.example.gamedemo.server.common.utils.AttributeUtils;
import com.example.gamedemo.server.common.utils.ParameterCheckUtils;
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
        InvokeMethod invokeMethod = ControllerManager.get(msg.split(" ")[0]);
        Class clazz = ControllerManager.getClassByCmd(msg.split(" ")[0]);
        if (invokeMethod != null) {
            TSession session = AttributeUtils.get(ctx.channel(), SessionAttributeKey.SESSION);
            boolean flag = ParameterCheckUtils.checkParams(session, msg, clazz);
            if (flag) {
                invokeMethod.invoke(session, msg);
            }

        } else {
            ctx.channel().writeAndFlush("指令有误\r\n");
        }
    }


}
