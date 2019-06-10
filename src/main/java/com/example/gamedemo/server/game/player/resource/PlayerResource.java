package com.example.gamedemo.server.game.player.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

/**
 * @author wengj
 * @description
 * @date 2019/5/31
 */
@Resource
public class PlayerResource implements ResourceInterface {
    @ExcelColumn(columnName = "playerId")
    private int playerId;

    @ExcelColumn(columnName = "playerName")
    private String playerName;

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public Object getId() {
        return playerId;
    }

    @Override
    public void postInit() {

    }
}
