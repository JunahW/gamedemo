package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.common.exception.RequestException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description: 账户业务实现层
 */
@Service
public class PlayerServiceImpl implements PlayerService {

  private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);
  @Autowired private PlayerManager playerManager;

  @Override
  public Player getPlayerById(long playerId) {
    logger.info("客户端查询accountId：{}", playerId);
    return playerManager.getPlayerEntByPlayerId(playerId).getPlayer();
  }

  @Override
  public boolean createPlayer(Player player) {
    PlayerEnt load = playerManager.getPlayerEntByPlayerId(player.getId());
    if (null != load) {
      logger.info("[{}]玩家已存在", player.getId());
      RequestException.throwException(I18nId.PLAYER_EXIST);
    }
    player.setJobName(playerManager.getPlayerResourceById(player.getJobId()).getJobName());
    PlayerEnt playerEnt = new PlayerEnt();
    // 设置起始地址
    player.setSceneId(SystemConstant.DEFAULT_SCENE);

    playerEnt.setPlayer(player);

    savePlayerEnt(player);
    logger.info("新增用户：{}", player.getJobName());
    return true;
  }

  @Override
  public Player selectPlayer(String accountId, Long playerId) {
    PlayerEnt playerEnt = playerManager.getPlayerEntByPlayerId(playerId);
    if (playerEnt != null && playerEnt.getAccountId().equals(accountId)) {
      Player player = playerEnt.getPlayer();
      logger.info("{}选择角色成功", playerEnt.getJobName());
      // 初始化玩家属性
      getPlayerAttrByPlayerId(player, playerId);
      /** 触发事件 */
      Scene scene = SpringContext.getSceneService().getSceneById(player.getSceneId());
      scene.enterScene(player);
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
    savePlayerEnt(player);
  }

  @Override
  public List<Player> getPlayerList() {
    List<Player> playerList = new ArrayList<>();

    return playerList;
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
        SpringContext.getPlayerService().getBaseAttributeResourceByPlayerType(player.getJobId());

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
