package com.example.gamedemo.common.net;

import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.common.MsgPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: wengj
 * @date: 2019/5/6
 * @description: 会话处理器
 */
public class SessionHandler extends SimpleChannelInboundHandler<MsgPacket> {

  private static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);

  @Override
  public void channelRead0(ChannelHandlerContext ctx, MsgPacket msg) throws Exception {
    ctx.fireChannelRead(msg);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    // 创建session
    logger.info("创建session");
    SessionManager.create(ctx.channel());
    ctx.fireChannelActive();
  }
}
