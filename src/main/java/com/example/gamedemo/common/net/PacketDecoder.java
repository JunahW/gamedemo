package com.example.gamedemo.common.net;

import com.example.gamedemo.common.utils.DecoderUtils;
import com.example.gamedemo.common.utils.ParameterCheckUtils;
import com.example.gamedemo.server.common.MsgPacket;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author wengj
 * @description：字符串装换为对象处理器
 * @date 2019/6/11
 */
public class PacketDecoder extends MessageToMessageDecoder<String> {

  @Override
  protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
    boolean checkCmd = ParameterCheckUtils.checkCmd(msg);
    if (!checkCmd) {
      SM_NoticeMessge message = SM_NoticeMessge.valueOf("指令有误");
      ctx.writeAndFlush(message);
      return;
    }

    boolean flag = ParameterCheckUtils.checkParams(msg);
    if (flag) {
      MsgPacket msgPacket = DecoderUtils.transformMsg2MsgPacket(msg);
      out.add(msgPacket);
    } else {
      SM_NoticeMessge message = SM_NoticeMessge.valueOf("请求参数有误");
      ctx.writeAndFlush(message);
    }
  }
}
