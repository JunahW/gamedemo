package com.example.gamedemo.server.game.scene.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneCommand;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description
 * @date 2019/7/9
 */
public class ChangeSceneCommand extends AbstractSceneCommand {
  private static final Logger logger = LoggerFactory.getLogger(ChangeSceneCommand.class);
  /** 玩家 */
  private Player player;

  /** 目的场景id */
  private int targetSceneId;

  public ChangeSceneCommand(int sceneId, Player player, int targetSceneId) {
    super(sceneId);
    this.player = player;
    this.targetSceneId = targetSceneId;
  }

  /**
   * @param sceneId
   * @param player
   * @param targetSceneId
   * @return
   */
  public static ChangeSceneCommand valueOf(int sceneId, Player player, int targetSceneId) {
    ChangeSceneCommand changeSceneCommand = new ChangeSceneCommand(sceneId, player, targetSceneId);
    return changeSceneCommand;
  }

  @Override
  public void doAction() {
    logger.info(
        "[{}][{}]请求切换地图从[{}]->[{}]",
        player.getSceneObjectType(),
        player.getId(),
        getSceneId(),
        targetSceneId);
    SpringContext.getSceneService().serverChangeScene(player, targetSceneId);
  }
}
