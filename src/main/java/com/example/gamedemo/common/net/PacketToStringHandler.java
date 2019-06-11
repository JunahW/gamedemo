package com.example.gamedemo.common.net;

import com.example.gamedemo.common.utils.JsonUtils;
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
        //TODO 兼容字符串类型
        String resultString;
        if (msg instanceof String) {
            resultString = msg.toString();
        } else {
            resultString = JsonUtils.serializeEntity(msg);
        }
        System.out.println(resultString);
        ctx.writeAndFlush(resultString + "\r\n", promise);
    }
}
