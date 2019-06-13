package com.example.gamedemo.common.session;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.player.model.Player;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description 游戏会话
 * @date 2019/5/6
 */
public class TSession {
    private static final Logger logger = LoggerFactory.getLogger(TSession.class);

    /**
     * 与客户端的管道
     */
    private final Channel channel;
    /**
     * 用户信息 用户信息可能为空。 只有在register(登录)之后，里面才会赋值
     */
    private Player player;

    private Account account;
    /**
     * 创建时间
     */
    private final long createTime;

    TSession(Channel channel) {
        this.channel = channel;
        this.createTime = System.currentTimeMillis();
    }

    /**
     * 往session里面写入player，一般是在选择角色
     *
     * @param player 玩家信息
     */
    void registerPlayer(Player player) {
        /** 将accounid放入player */
        // player.setAccountId(account.getAccountId());
        this.player = player;
    }

    /**
     * 往session里面写入user，一般是在登录之后调用
     *
     * @param account 用户信息
     */
    void registerAccount(Account account) {
        this.account = account;
    }

    /**
     * 关闭与客户端的连接
     */
    void close() {
        if (channel == null) {
            return;
        }
        try {
            if (channel.isActive() || channel.isOpen()) {
                channel.close().sync();
            }
        } catch (InterruptedException e) {
            logger.error("channel.close find error ", e);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Channel getChannel() {
        return channel;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
