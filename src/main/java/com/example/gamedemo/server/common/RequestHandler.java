package com.example.gamedemo.server.common;

import com.example.gamedemo.common.constant.SessionAttributeKey;
import com.example.gamedemo.common.dispatcher.ControllerManager;
import com.example.gamedemo.common.dispatcher.InvokeMethod;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.common.utils.AttributeUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description 请求分发
 * @date 2019/5/7
 */
public class RequestHandler extends SimpleChannelInboundHandler<MsgPacket> {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgPacket msgPacket) throws Exception {
        logger.info("===>receive msg:" + msgPacket);
        //执行请求分发
        //根据指令获取当前的指令多对应的controller
        InvokeMethod invokeMethod = ControllerManager.get(msgPacket.getCmd());
        if (invokeMethod != null) {
            TSession session = AttributeUtils.get(ctx.channel(), SessionAttributeKey.SESSION);
            //boolean flag = ParameterCheckUtils.checkParams(session, msg, clazz);
            if (true) {
                invokeMethod.invoke(session, msgPacket.getMsg());
            }

        } else {
            ctx.channel().writeAndFlush("指令有误\r\n");
        }
    }


}
