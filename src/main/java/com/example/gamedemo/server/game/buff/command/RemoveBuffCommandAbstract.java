package com.example.gamedemo.server.game.buff.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneCommand;
import com.example.gamedemo.server.game.buff.model.BuffContainer;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
public class RemoveBuffCommandAbstract extends AbstractSceneCommand {
  /** buff容器 */
  private BuffContainer buffContainer;

  /** 移除的buff */
  private Integer buffId;

  public RemoveBuffCommandAbstract(int sceneId, BuffContainer buffContainer, Integer buffId) {
    super(sceneId);
    this.buffContainer = buffContainer;
    this.buffId = buffId;
  }

  /**
   * @param sceneId
   * @param buffContainer
   * @param buffId
   * @return
   */
  public static RemoveBuffCommandAbstract valueOf(
      int sceneId, BuffContainer buffContainer, Integer buffId) {
    RemoveBuffCommandAbstract removeBuffCommand =
        new RemoveBuffCommandAbstract(sceneId, buffContainer, buffId);
    return removeBuffCommand;
  }

  @Override
  public void doAction() {
    buffContainer.removeBuff(buffId);
  }
}
