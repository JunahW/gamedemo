package com.example.gamedemo.server.common;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author wengj
 * @description：消息报转换为字符串处理器
 * @date 2019/6/11
 */
public class PacketToStringHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        //TODO
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println(msg);
        ctx.writeAndFlush(msg, promise);
    }
}
