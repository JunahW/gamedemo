package com.example.gamedemo.server.game.player.packet;

/**
 * @author wengj
 * @description：创建玩家消息
 * @date 2019/5/27
 */
public class CM_CreatePlayer {

    /**
     * 账户id
     */
    private String playerId;

    /**
     * 玩家类型
     */
    private String playerType;

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerType() {
        return playerType;
    }
}
