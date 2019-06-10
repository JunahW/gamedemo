package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.PlayerAttributeContainer;
import com.example.gamedemo.server.game.attribute.constant.AttributeModelIdEnum;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.bag.model.EquipItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.equip.constant.EquipmentType;
import com.example.gamedemo.server.game.equip.model.Slot;
import com.example.gamedemo.server.game.equip.resource.EquipAttrResource;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.example.gamedemo.server.game.player.entity.PlayerEnt;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.resource.BaseAttributeResource;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Autowired
    private Accessor accessor;


    @Autowired
    private PlayerManager playerManager;

    private static final ObjectMapper mapper = new ObjectMapper();


    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);


    @Override
    public Player getPlayerById(String playerId) {
        logger.info("客户端查询accountId：{}", playerId);
        return accessor.load(PlayerEnt.class, playerId).getPlayer();
    }

    @Override
    public int createPlayer(Player player) {
        PlayerEnt load = accessor.load(PlayerEnt.class, player.getPlayerId());
        if (null != load) {
            logger.info("[{}]玩家已存在", player.getPlayerId());
            RequestException.throwException(I18nId.PLAYER_NO_EXIST);
        }
        player.setPlayerName(playerManager.getPlayerResourceById(player.getPlayerType()).getPlayerName());
        PlayerEnt playerEnt = new PlayerEnt();
        //设置其实地址
        player.setSceneResource(playerManager.getSceneResourceById(SystemConstant.DEFAULT_SCENE));
        playerEnt.setPlayer(player);

        playerEnt.serialize();
        logger.info("新增用户：{}", player.getPlayerName());

        String save = accessor.save(PlayerEnt.class, playerEnt);
        if (save != null) {
            logger.info("新增用户成功");
            return 1;
        } else {
            logger.info("新增用户失败");
            return 0;
        }

    }

    @Override
    public Player selectPlayer(String playerId) {
        PlayerEnt playerEnt = playerManager.getPlayerEntByPlayerId(playerId);
        if (playerEnt != null) {
            playerEnt.deSerialize();
            Player player = playerEnt.getPlayer();
            logger.info("{}选择角色成功", playerEnt.getPlayerName());
            //获取玩家的基础属性
           /* BaseAttributeResource baseAttribute = SpringContext.getPlayerService().getBaseAttributeResourceByPlayerType(player.getPlayerType());
            player.getPlayerAttributeContainer().putAndComputeAttributes(AttributeModelIdEnum.BASE, baseAttribute.getPlayerBaseAttribute());
*/
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
        SceneResource sceneResource = player.getSceneResource();
        int[][] sceneMap = sceneResource.getSceneMap();
        if (sceneResource.getWidth() - 1 < x || sceneResource.getHeight() - 1 < y) {
            logger.info("请求参数不合法");
            return false;
        }
        //修改玩家当前位置
        if (sceneMap[x][y] == 0) {
            logger.info("该位置有障碍物");
            return false;
        }

        /**
         * 移动位置
         */
        int currentx = player.getX();
        int currenty = player.getY();
        player.setX(x);
        player.setY(y);
        logger.info("({},{})从移动到({},{})", currentx, currenty, x, y);
        return true;
    }

    @Override
    public BaseAttributeResource getBaseAttributeResourceByPlayerType(int playerType) {
        return playerManager.getAttributeResourceByPlayerType(playerType);
    }

    @Override
    public ConcurrentMap<AttributeTypeEnum, Attribute> getPlayerAttrByPlayerId(Player player, String playerId) {
        PlayerEnt playerEnt = playerManager.getPlayerEntByPlayerId(playerId);
        if (playerEnt == null) {
            logger.info("[{}]该玩家不存在", playerId);
            RequestException.throwException(I18nId.PLAYER_NO_EXIST);
        }
        Player checkedPlayer = playerEnt.getPlayer();
        //玩家的积存属性
        BaseAttributeResource baseAttribute = SpringContext.getPlayerService().getBaseAttributeResourceByPlayerType(checkedPlayer.getPlayerType());

        //玩家容器
        PlayerAttributeContainer playerAttributeContainer = checkedPlayer.getPlayerAttributeContainer();
        //玩家基本属性
        playerAttributeContainer.putAttributeSet(AttributeModelIdEnum.BASE, baseAttribute.getPlayerBaseAttribute());

        //获取装备栏
        EquipStorage equipBar = checkedPlayer.getEquipBar();
        Slot[] slots = equipBar.getSlots();
        for (Slot slot : slots) {
            if (slot != null && slot.getEquipItem() != null) {
                EquipItem equipItem = slot.getEquipItem();
                ItemResource itemResource = SpringContext.getItemService().getItemResourceByItemResourceId(equipItem.getItemResourceId());
                EquipmentType equipmentType = EquipmentType.getEquipmentTypeId(itemResource.getPosition());
                EquipAttrResource equipAttrResource = SpringContext.getEquipmentService().getEquipAttrResourceById(itemResource.getItemId());
                playerAttributeContainer.putAttributeSet(equipmentType, equipAttrResource.getAttributes());
            }
        }

        //计算属性的值
        playerAttributeContainer.compute();
        return playerAttributeContainer.getAttributeMap();
    }
}
