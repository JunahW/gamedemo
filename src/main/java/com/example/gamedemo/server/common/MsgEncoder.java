package com.example.gamedemo.server.common;

import com.example.gamedemo.common.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wengj
 * @description：消息编码器
 * @date 2019/6/10
 */
public class MsgEncoder extends MessageToByteEncoder<MsgPacket> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MsgPacket msgPacket, ByteBuf out) throws Exception {
        if (msgPacket != null) {
            System.out.println(msgPacket);
            String msg = JsonUtils.serializeEntity(msgPacket.getMsg());
            String sendMsg = msgPacket.getCmd() + " " + msg + "\r\n";
            out.writeBytes(sendMsg.getBytes());
        }
    }
}
