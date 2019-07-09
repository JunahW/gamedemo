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
public class EnterSceneCommand extends AbstractSceneCommand {

  private static final Logger logger = LoggerFactory.getLogger(EnterSceneCommand.class);

  private Player player;

  public EnterSceneCommand(int sceneId, Player player) {
    super(sceneId);
    this.player = player;
  }

  /**
   * @param player
   * @param targetId
   * @return
   */
  public static EnterSceneCommand valueOf(Player player, int targetId) {
    EnterSceneCommand enterSceneCommand = new EnterSceneCommand(targetId, player);
    return enterSceneCommand;
  }

  @Override
  public void doAction() {
    logger.info("[{}][{}]请求进入地图[{}]", player.getSceneObjectType(), player.getId(), getSceneId());
    SpringContext.getSceneService().enterScene(player, getSceneId());
  }
}
