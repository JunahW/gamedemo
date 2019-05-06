package com.example.gamedemo.server.game;

import com.example.gamedemo.server.common.session.SessionManager;
import com.example.gamedemo.server.common.session.TSession;
import com.example.gamedemo.server.game.account.model.Account;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: wengj
 * @date: 2019/5/6
 * @description: 会话处理器
 */
public class SessionHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);


    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.info("session handler invoke...");

        Channel channel = ctx.channel();

        TSession session = SessionManager.getSessionByChannel(channel);
        System.out.println(session);

        Account account = session.getAccount();

        // 登陆之后account属性 这里可以对登陆放行
        /*if (account != null || msg.startsWith(AccountCmd.LOGIN)) {
            ctx.fireChannelRead(msg);
        } else {
            channel.writeAndFlush("请先登录");
            return;
        }*/

        ctx.fireChannelRead(msg);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //创建session
        SessionManager.create(ctx.channel());
        ctx.fireChannelActive();
    }
}
