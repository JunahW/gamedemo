package com.example.gamedemo.server.common.session;

import com.example.gamedemo.server.common.constant.SessionAttributeKey;
import com.example.gamedemo.server.common.utils.AttributeUtils;
import com.example.gamedemo.server.game.account.model.Account;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: wengj
 * @date: 2019/5/6
 * @description: 会话管理
 */
public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static final SessionManager instance = new SessionManager();

    public static SessionManager getInstance() {
        return instance;
    }

    private SessionManager() {
        logger.info("SessionManager init success");
    }

    /**
     * 已经登录的session管理
     */
    private static ConcurrentMap<String, TSession> accountIdSessionMap = new ConcurrentHashMap<>(16);

    public static TSession getSession(String accountId) {
        return accountIdSessionMap.get(accountId);
    }

    /**
     * 创建session
     *
     * @param channel 与客户端连接的管道
     * @return session
     */
    public static TSession create(Channel channel) {
        TSession session = getSessionByChannel(channel);
        if (session == null) {
            session = new TSession(channel);
            AttributeUtils.set(channel, SessionAttributeKey.SESSION, session);
            logger.info("session 创建成功");
        } else {
            logger.error("新连接建立时已存在Session，注意排查原因 " + channel.toString());
        }
        return session;
    }

    /**
     * 注册sesson
     *
     * @param session session
     * @param account 用户
     */
    public static void register(TSession session, Account account) {
        session.registerAccount(account);
        accountIdSessionMap.put(session.getAccount().getAcountId(), session);
    }

    /**
     * 通过channel关闭session
     *
     * @param channel 与客户端连接的管道
     */
    public static void close(Channel channel) {
        TSession session = getSessionByChannel(channel);
        if (session != null) {
            close(session);
        }
    }

    /**
     * 关闭session
     *
     * @param session 要关闭的session
     */
    private static void close(TSession session) {
        unregister(session);
        session.close();
        logger.info("session close success");
    }


    /**
     * 从map中移除session
     *
     * @param session
     */
    private static void unregister(TSession session) {
        if (session != null) {
            AttributeUtils.set(session.getChannel(), SessionAttributeKey.SESSION, null);

            Account account = session.getAccount();
            if (account != null) {
                boolean remove = accountIdSessionMap.remove(account.getAcountId(), session);
                logger.info("Session unregister, userId={}, remove={}", account.getAcountId(), remove);
            }
        }
    }

    /**
     * 获取session
     *
     * @param channel
     * @return
     */
    public static TSession getSessionByChannel(Channel channel) {
        return AttributeUtils.get(channel, SessionAttributeKey.SESSION);
    }

    /**
     * 发送信息
     *
     * @param channel
     * @param msg
     */
    public static void sendMessage(Channel channel, String msg) {
        sendMessage(getSessionByChannel(channel), msg);
    }

    /**
     * 发送信息
     *
     * @param session
     * @param msg
     */
    public static void sendMessage(TSession session, String msg) {
        session.getChannel().writeAndFlush(msg);
    }

    /**
     * 通过Cha
     *
     * @param channel
     * @return
     */
    public static Account getAccountByChannel(Channel channel) {
        return AttributeUtils.get(channel, SessionAttributeKey.SESSION).getAccount();
    }
}
