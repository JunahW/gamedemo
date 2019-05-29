package com.example.gamedemo.server.game.bag.packet;

/**
 * @author wengj
 * @description:添加道具信息
 * @date 2019/5/29
 */
public class CM_AddItem {
    private String itemResourceId;

    public String getItemResourceId() {
        return itemResourceId;
    }

    public void setItemResourceId(String itemResourceId) {
        this.itemResourceId = itemResourceId;
    }
}
