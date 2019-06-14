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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

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
  public int createPlayer(Player player) {
    PlayerEnt load = accessor.load(PlayerEnt.class, player.getId());
    if (null != load) {
      logger.info("[{}]玩家已存在", player.getId());
      RequestException.throwException(I18nId.PLAYER_NO_EXIST);
    }
    player.setPlayerName(
        playerManager.getPlayerResourceById(player.getPlayerType()).getPlayerName());
    PlayerEnt playerEnt = new PlayerEnt();
    // 设置其实地址
    player.setSceneId(SystemConstant.DEFAULT_SCENE);
    playerEnt.setPlayer(player);

    playerEnt.serialize();
    logger.info("新增用户：{}", player.getPlayerName());

    Serializable save = accessor.save(PlayerEnt.class, playerEnt);
    if (save != null) {
      logger.info("新增用户成功");
      return 1;
    } else {
      logger.info("新增用户失败");
      return 0;
    }
  }

  @Override
  public Player selectPlayer(String accountId, Long playerId) {
    PlayerEnt playerEnt = playerManager.getPlayerEntByPlayerId(playerId);
    if (playerEnt != null && playerEnt.getAccountId().equals(accountId)) {
      playerEnt.deSerialize();
      Player player = playerEnt.getPlayer();
      logger.info("{}选择角色成功", playerEnt.getPlayerName());
      return player;
    } else {
      logger.warn("{}该玩家还未创建", playerId);
      RequestException.throwException(I18nId.PLAYER_NO_EXIST);
    }
    return null;
  }

  @Override
  public void updateAccount(Player player) {
    logger.info("异步保存数据");
    PlayerEnt playerEnt = new PlayerEnt();
    playerEnt.setPlayer(player);
    playerEnt.serialize();
    accessor.saveOrUpdate(PlayerEnt.class, playerEnt);
  }

  @Override
  public List<Player> getAccountList() {
    List<Player> playerList = new ArrayList<>();

    return playerList;
  }

  @Override
  public boolean move2Coordinate(Player player, int x, int y) {
    int sceneId = player.getSceneId();
    // TODO 移动位置
    /*    int[][] sceneMap = sceneResource.getSceneMap();
    if (sceneResource.getWidth() - 1 < x || sceneResource.getHeight() - 1 < y) {
      logger.info("请求参数不合法");
      return false;
    }
    // 修改玩家当前位置
    if (sceneMap[x][y] == 0) {
      logger.info("该位置有障碍物");
      return false;
    }

    */
    /** 移动位置 */
    /*
    int currentx = player.getX();
    int currenty = player.getY();
    player.setX(x);
    player.setY(y);
    logger.info("({},{})从移动到({},{})", currentx, currenty, x, y);*/
    return true;
  }

  @Override
  public BaseAttributeResource getBaseAttributeResourceByPlayerType(int playerType) {
    return playerManager.getAttributeResourceByPlayerType(playerType);
  }

  @Override
  public ConcurrentMap<AttributeTypeEnum, Attribute> getPlayerAttrByPlayerId(
      Player player, Long playerId) {
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
