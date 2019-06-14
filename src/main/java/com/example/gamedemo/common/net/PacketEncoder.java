package com.example.gamedemo.common.net;

import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.utils.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wengj
 * @description：消息报转换为字符串处理器
 * @date 2019/6/11
 */
public class PacketEncoder extends MessageToMessageEncoder<Object> {
  private static Logger logger = LoggerFactory.getLogger(PacketEncoder.class);

  @Override
  protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
    String resultString;
    if (msg instanceof String) {
      resultString = msg.toString();
    } else {
      resultString = JsonUtils.serializeEntity(msg);
    }
    logger.info(resultString);
    out.add(resultString + SystemConstant.MSG_END_TOKEN);
  }
}
