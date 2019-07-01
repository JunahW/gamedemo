package com.example.gamedemo.server.game.buff.command;

import com.example.gamedemo.common.executer.SceneCommand;
import com.example.gamedemo.server.game.buff.model.BuffContainer;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
public class RemoveBuffCommand extends SceneCommand {
  /** buff容器 */
  private BuffContainer buffContainer;

  /** 移除的buff */
  private Integer buffId;

  public RemoveBuffCommand(int sceneId, BuffContainer buffContainer, Integer buffId) {
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
  public static RemoveBuffCommand valueOf(
      int sceneId, BuffContainer buffContainer, Integer buffId) {
    RemoveBuffCommand removeBuffCommand = new RemoveBuffCommand(sceneId, buffContainer, buffId);
    return removeBuffCommand;
  }

  @Override
  public void doAction() {
    buffContainer.removeBuff(buffId);
  }
}
