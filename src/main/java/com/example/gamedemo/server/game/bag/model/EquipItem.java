package com.example.gamedemo.server.game.bag.model;

/**
 * @author wengj
 * @description:装备物件
 * @date 2019/5/30
 */
public class EquipItem extends AbstractItem {
    /**
     * 玩家类型（职业）
     */
    private String[] playerTypes;

    /**
     * 装备位置
     */
    private int position;

    public String[] getPlayerTypes() {
        return playerTypes;
    }

    public int getPosition() {
        return position;
    }

    public void setPlayerTypes(String[] playerTypes) {
        this.playerTypes = playerTypes;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
