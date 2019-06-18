package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.PlayerAttributeContainer;
import com.example.gamedemo.server.game.attribute.constant.AttributeModelIdEnum;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.player.entity.PlayerEnt;
import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.resource.BaseAttributeResource;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description: 账户业务实现层
 */
@Service(value = "accountService")
public class PlayerServiceImpl implements PlayerService {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);
  @Autowired private Accessor accessor;
  @Autowired private PlayerManager playerManager;

  @Override
  public Player getPlayerById(String playerId) {
    logger.info("客户端查询accountId：{}", playerId);
    return accessor.load(PlayerEnt.class, playerId).getPlayer();
  }

  @Override
  public boolean createPlayer(Player player) {
    PlayerEnt load = accessor.load(PlayerEnt.class, player.getId());
    if (null != load) {
      logger.info("[{}]玩家已存在", player.getId());
      RequestException.throwException(I18nId.PLAYER_NO_EXIST);
    }
    player.setPlayerName(playerManager.getPlayerResourceById(player.getPlayerType()).getRoleName());
    PlayerEnt playerEnt = new PlayerEnt();
    // 设置起始地址
    player.setSceneId(SystemConstant.DEFAULT_SCENE);
    Scene scene = SpringContext.getSceneService().getSceneById(SystemConstant.DEFAULT_SCENE);
    scene.enterScene(player);
    playerEnt.setPlayer(player);

    playerEnt.serialize();
    logger.info("新增用户：{}", player.getPlayerName());

    Serializable save = accessor.save(PlayerEnt.class, playerEnt);
    if (save != null) {
      logger.info("新增用户成功");
      return true;
    } else {
      logger.info("新增用户失败");
      return false;
    }
  }

  @Override
  public Player selectPlayer(String accountId, Long playerId) {
    PlayerEnt playerEnt = playerManager.getPlayerEntByPlayerId(playerId);
    if (playerEnt != null && playerEnt.getAccountId().equals(accountId)) {
      playerEnt.deSerialize();
      Player player = playerEnt.getPlayer();
      logger.info("{}选择角色成功", playerEnt.getPlayerName());
      // 初始化玩家属性
      getPlayerAttrByPlayerId(player, playerId);
      return player;
    } else {
      logger.warn("{}该玩家还未创建", playerId);
      RequestException.throwException(I18nId.PLAYER_NO_EXIST);
    }
    return null;
  }

  @Override
  public void updatePlayer(Player player) {
    logger.info("异步保存数据");
    PlayerEnt playerEnt = new PlayerEnt();
    playerEnt.setPlayer(player);
    playerEnt.serialize();
    accessor.saveOrUpdate(PlayerEnt.class, playerEnt);
  }

  @Override
  public List<Player> getPlayerList() {
    List<Player> playerList = new ArrayList<>();

    return playerList;
  }

  @Override
  public boolean move2Coordinate(Player player, int x, int y) {
    int sceneId = player.getSceneId();

    SceneResource sceneResource = SpringContext.getSceneService().getSceneResourceById(sceneId);
    int[][] sceneMap = sceneResource.getSceneMap();
    // TODO 移动位置

    if (sceneResource.getWidth() - 1 < x || sceneResource.getHeight() - 1 < y) {
      logger.info("请求位置参数不合法");
      RequestException.throwException(I18nId.SCENE_POSITION_ERROR);
    }
    // 修改玩家当前位置
    if (sceneMap[x][y] == 0) {
      logger.info("该位置有障碍物");
      RequestException.throwException(I18nId.SCENE_OBSTACLE);
    }

    /** 移动位置 */
    int preX = player.getX();
    int preY = player.getY();
    player.setX(x);
    player.setY(y);
    logger.info("({},{})从移动到({},{})", preX, preY, x, y);
    return true;
  }

  @Override
  public BaseAttributeResource getBaseAttributeResourceByPlayerType(int playerType) {
    return playerManager.getAttributeResourceByPlayerType(playerType);
  }

  @Override
  public Map<AttributeTypeEnum, Attribute> getPlayerAttrByPlayerId(Player player, Long playerId) {
    PlayerEnt playerEnt = playerManager.getPlayerEntByPlayerId(playerId);
    if (playerEnt == null) {
      logger.info("[{}]该玩家不存在", playerId);
      RequestException.throwException(I18nId.PLAYER_NO_EXIST);
    }

    /** 触发事件，交给各个receiver处理 */
    Player checkedPlayer = playerEnt.getPlayer();
    EventBusManager.submitEvent(PlayerLoadEvent.valueof(checkedPlayer));

    // 计算属性的值
    PlayerAttributeContainer playerAttributeContainer = checkedPlayer.getPlayerAttributeContainer();
    playerAttributeContainer.compute();
    return playerAttributeContainer.getAttributeMap();
  }

  @Override
  public void computePlayerBaseAttributes(PlayerLoadEvent event) {
    logger.info("处理玩家加载事件");
    Player player = event.getPlayer();
    // 玩家的积存属性
    BaseAttributeResource baseAttribute =
        SpringContext.getPlayerService()
            .getBaseAttributeResourceByPlayerType(player.getPlayerType());

    // 玩家容器
    PlayerAttributeContainer playerAttributeContainer = player.getPlayerAttributeContainer();
    // 玩家基本属性
    playerAttributeContainer.putAttributeSet(
        AttributeModelIdEnum.BASE, baseAttribute.getPlayerBaseAttribute());
  }

  @Override
  public void savePlayerEnt(Player player) {
    playerManager.savePlayerEnt(PlayerEnt.valueOf(player));
  }
}
