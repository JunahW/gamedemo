package com.example.gamedemo.server.game.dungeon.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.constant.SceneTypeEnum;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: wengj
 * @date: 2019/7/10
 * @description: 副本业务层
 */
@Service
public class DungeonServiceImpl implements DungeonService {
  private static final Logger logger = LoggerFactory.getLogger(DungeonServiceImpl.class);

  @Override
  public void enterDungeon(Player player, int sceneId) {
    // 创建副本
    MapResource sceneResource = SpringContext.getSceneService().getSceneResourceById(sceneId);

    if (sceneResource == null
        || !sceneResource.getSceneTypeEnum().equals(SceneTypeEnum.DUNGEON_SCENE)) {
      logger.info("该场景配置资源不存在");
      RequestException.throwException(I18nId.SCENE_RESOURCE_NO_EXIST);
    }

    Scene scene = Scene.valueOf(sceneId);
  }

  @Override
  public void leaveDungeon(Player player) {}
}
