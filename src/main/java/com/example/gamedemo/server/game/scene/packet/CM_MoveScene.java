package com.example.gamedemo.server.game.scene.packet;

/**
 * @author wengj
 * @description:穿越进入场景
 * @date 2019/5/27
 */
public class CM_MoveScene {
    /**
     * 场景id
     */
    private String sceneId;


    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }
}
