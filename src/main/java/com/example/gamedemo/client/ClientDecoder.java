package com.example.gamedemo.client;

import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author wengj
 * @description：字符串装换为对象处理器
 * @date 2019/6/11
 */
public class ClientDecoder extends MessageToMessageDecoder<String> {

  @Override
  protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {

    String[] split = msg.split(SystemConstant.CLASS_JSON_SPLIT_TOKEN);
    Object returnMsg = null;
    if (split != null && split.length == 2) {
      returnMsg = JsonUtils.deSerializeEntity(split[1], Class.forName(split[0]));
    } else {
      returnMsg = SM_NoticeMessge.valueOf("服务端返回数据有误");
    }
    out.add(returnMsg);
  }
}
