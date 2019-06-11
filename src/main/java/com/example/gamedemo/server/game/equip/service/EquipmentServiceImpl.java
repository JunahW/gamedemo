package com.example.gamedemo.server.game.equip.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.PlayerAttributeContainer;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.model.EquipItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.equip.constant.EquipmentEnhanceType;
import com.example.gamedemo.server.game.equip.constant.EquipmentType;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;
import com.example.gamedemo.server.game.equip.model.Consume;
import com.example.gamedemo.server.game.equip.model.Slot;
import com.example.gamedemo.server.game.equip.resource.EquipAttrResource;
import com.example.gamedemo.server.game.equip.resource.EquipEnhanceResource;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
import com.example.gamedemo.server.game.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wengj
 * @description：装备业务层
 * @date 2019/5/30
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {
    private static final Logger logger = LoggerFactory.getLogger(EquipmentServiceImpl.class);


    @Autowired
    private EquipmentManager equipmentManager;


    @Override
    public boolean equip(Player player, long equipId) {
        AbstractItem item = player.getPack().getStorageItemByObjectId(equipId);
        if (item == null) {
            logger.warn("该装备[{}]不存在", equipId);
            RequestException.throwException(I18nId.EQUIPMENT_NO_EXIST);
        }

        int itemResourceId = item.getItemResourceId();
        ItemResource itemResource = SpringContext.getItemService().getItemResourceByItemResourceId(itemResourceId);
        /**
         * 如果非装备类型，直接返回
         */
        if (!(item instanceof EquipItem)) {
            logger.info("[{}]该道具不是装备类型", itemResource.getName());
            RequestException.throwException(I18nId.ITEM_NO_EQUIPMENT);
        }

        EquipItem equipItem = (EquipItem) item;


        //判断是否可装备
        if (!verifyJob(player, equipItem)) {
            logger.info("玩家职业和道具类型不匹配");
            RequestException.throwException(I18nId.PLAYER_TYPE_NO_MATCH_EQUIPMENT);
        }

        //减少背包道具
        boolean reduce = player.getPack().reduceStorageItemByObjectId(equipId, 1);
        if (!reduce) {
            logger.info("扣除道具失败");
            RequestException.throwException(I18nId.ITEM_REDUCE_FAIL);
        }

        //装备物品
        AbstractItem oreEquip = player.getEquipBar().equip(equipItem);
        // 替代装备,将旧装备放回背包
        if (oreEquip != null) {
            player.getPack().addStorageItem(oreEquip);
        }

        //更细属性容器
        EquipAttrResource equipAttrResource = equipmentManager.getequipAttrResourceById(itemResourceId);
        EquipmentType equipmentType = EquipmentType.getEquipmentTypeId(itemResource.getPosition());
        player.getPlayerAttributeContainer().putAndComputeAttributes(equipmentType, equipAttrResource.getAttributes());


        //保存装备栏
        saveEquipmentStorageEnt(player);
        //保存背包
        SpringContext.getItemService().saveItemStorageEnt(player);
        logger.info("已穿上[{}]装备", itemResource.getName());
        return true;
    }

    @Override
    public boolean unEquip(Player player, int position) {
        //检查参数是否合法
        if (!isLegal(position)) {
            logger.info("请求从参数有误position[{}]", position);
            RequestException.throwException(I18nId.POSITION_NO_DEFINE);
        }

        AbstractItem equipItem = player.getEquipBar().unEquip(position);
        if (equipItem == null) {
            logger.info("该部位不存在物件", EquipmentType.getEquipmentTypeId(position));
            RequestException.throwException(I18nId.POSITION_NO_EXIST_EQUIPMENT);
        }

        int itemResourceId = equipItem.getItemResourceId();
        ItemResource itemResource = SpringContext.getItemService().getItemResourceByItemResourceId(itemResourceId);
        //更细属性容器
        EquipmentType equipmentType = EquipmentType.getEquipmentTypeId(itemResource.getItemType());
        player.getPlayerAttributeContainer().removeAndComputeAttributeSet(equipmentType);
        logger.info("[{}]部位已移除装备[{}]", EquipmentType.getEquipmentTypeId(position), equipItem.getObjectId());
        return true;
    }

    @Override
    public AbstractItem getEquipItemByGuid(Player player, long guid) {
        AbstractItem equipment = player.getEquipBar().getEquipmentByGuid(guid);
        return equipment;
    }

    @Override
    public AbstractItem getEquipItemByPosition(Player player, int position) {
        AbstractItem equipment = player.getEquipBar().getEquipmentByPosition(position);
        return equipment;
    }

    @Override
    public void saveEquipmentStorageEnt(Player player) {
        equipmentManager.saveEquipStorageEnt(getEquipStorageEnt(player.getPlayerId()));

    }

    @Override
    public EquipStorageEnt getEquipStorageEnt(String playerId) {
        return equipmentManager.getEquipStorageEnt(playerId);
    }

    /**
     * 判断装备部位是否合法
     *
     * @param position
     * @return
     */
    private boolean isLegal(int position) {
        if (position < 0 || position >= EquipmentType.values().length) {
            return false;
        }
        return true;
    }


    /**
     * 检查装备和玩家职业是否匹配
     *
     * @param player
     * @param equipItem
     * @return
     */
    private boolean verifyJob(Player player, EquipItem equipItem) {
        /**
         * 判断玩家类型和装备类型是否匹配
         */
        ItemResource itemResource = SpringContext.getItemService().getItemResourceByItemResourceId(equipItem.getItemResourceId());
        int[] playerTypes = itemResource.getPlayerTypes();
        for (int playerType : playerTypes) {
            if (playerType == player.getPlayerType()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean enhanceEquip(Player player, int position) {
        ItemStorage pack = player.getPack();
        EquipStorage equipBar = player.getEquipBar();
        Slot slot = equipBar.getSlotByPosision(position);
        if (slot == null) {
            logger.info("[{}]卡槽位置参数不合法");
            RequestException.throwException(I18nId.SLOT_POSITION_ILLEGAL);
        }
        int level = slot.getLevel();
        EquipEnhanceResource equipEnhanceResource = equipmentManager.getEquipEnhanceResourceByPositionAndLevel(position, level + 1);
        if (equipEnhanceResource == null) {
            logger.info("该位置已升级到最高级别");
            RequestException.throwException(I18nId.SLOT_LEVEL_CELL);
        }

        /**
         * 检查所需消耗物
         */
        List<Consume> consumeList = equipEnhanceResource.getConsumeList();
        boolean isEnough = false;
        for (Consume consume : consumeList) {
            //消耗背包物品
            int itemId = consume.getItemId();
            int quantity = consume.getQuantity();
            //检查背包是否满足条件
            isEnough = pack.checkPackItemQuantity(itemId, quantity);
            if (isEnough == false) {
                break;
            }
        }
        if (isEnough == false) {
            logger.info("装备栏卡槽升级所需的道具数量不足");
            RequestException.throwException(I18nId.SLOT_UP_ITEM_NO_ENOUGH);
        }

        /**
         * 消耗道具
         */
        for (Consume consume : consumeList) {
            //消耗背包物品
            int itemId = consume.getItemId();
            int quantity = consume.getQuantity();
            pack.reduceStorageItemByItemResourceId(itemId, quantity);
        }
        //保存背包
        SpringContext.getItemService().saveItemStorageEnt(player);

        /**
         * 修改卡槽等级
         */
        slot.setLevel(equipEnhanceResource.getLevel());

        //保存装备栏
        saveEquipmentStorageEnt(player);


        /**
         * TODO增强的属性
         */
        List<Attribute> attributeList = equipEnhanceResource.getAttributeList();


        return isEnough;
    }

    @Override
    public EquipAttrResource getEquipAttrResourceById(int id) {
        return equipmentManager.getequipAttrResourceById(id);
    }

    @Override
    public void computeEquipModelAttributes(PlayerLoadEvent event) {
        logger.info("处理玩家加载事件");
        Player player = event.getPlayer();
        //玩家容器
        PlayerAttributeContainer playerAttributeContainer = player.getPlayerAttributeContainer();
        //获取装备栏
        EquipStorage equipBar = player.getEquipBar();
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
        //TODO 是否计算
        // playerAttributeContainer.compute();
    }

    @Override
    public void computeEquipEnhanceModelAttributes(PlayerLoadEvent event) {
        logger.info("处理玩家加载事件");
        Player player = event.getPlayer();
        //玩家容器
        PlayerAttributeContainer playerAttributeContainer = player.getPlayerAttributeContainer();
        //获取装备栏
        EquipStorage equipBar = player.getEquipBar();
        Slot[] slots = equipBar.getSlots();
        for (int position = 0; position < slots.length; position++) {
            if (slots[position] != null) {
                int level = slots[position].getLevel();
                EquipEnhanceResource resource = equipmentManager.getEquipEnhanceResourceByPositionAndLevel(position, level);
                if (resource == null) {
                    continue;
                }
                List<Attribute> attributeList = resource.getAttributeList();
                /**
                 * 部位名称
                 */
                playerAttributeContainer.putAttributeSet(EquipmentEnhanceType.getEquipmentEnhanceTypeByPosition(position), attributeList);

            }
        }
        //TODO 是否计算
        // playerAttributeContainer.compute();
    }
}
