package com.example.gamedemo.server.game.scene.packet;

/**
 * @author wengj
 * @description:穿越进入场景
 * @date 2019/5/27
 */
public class CM_GotoScene {
  /** 场景id */
  private int sceneId;

  public int getSceneId() {
    return sceneId;
  }

  public void setSceneId(int sceneId) {
    this.sceneId = sceneId;
  }
}
