package com.example.gamedemo.server.common;

import com.example.gamedemo.common.utils.DecoderUtils;
import com.example.gamedemo.common.utils.ParameterCheckUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wengj
 * @description：字符串装换为对象处理器
 * @date 2019/6/11
 */
public class StringToPacketHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        boolean checkCmd = ParameterCheckUtils.checkCmd(msg);
        if (!checkCmd) {
            ctx.writeAndFlush("指令有误\r\n");
            return;
        }

        boolean flag = ParameterCheckUtils.checkParams(msg);
        if (flag) {
            MsgPacket msgPacket = DecoderUtils.transformMsg2MsgPacket(msg);
            ctx.fireChannelRead(msgPacket);
        } else {
            ctx.writeAndFlush("请求参数有误\r\n");
        }

    }
}
