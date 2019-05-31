package com.example.gamedemo.server.game.equip.service;

import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.equip.constant.EquipmentType;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;
import com.example.gamedemo.server.game.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return false;
        }
        String itemResourceId = item.getItemResourceId();
        ItemResource itemResource = SpringContext.getItemService().getItemResourceByItemResourceId(itemResourceId);

        //TODO 判断是否可装备

        //装备物品
        int position = itemResource.getPosition();
        if (!isLegal(position)) {
            logger.info("[{}]该道具不是装备类型", itemResource.getName());
            return false;
        }
        player.getEquipBar().equip(item, position);
        //保存
        saveEquipmentStorageEnt(player);

        //减少背包道具
        player.getPack().reduceStorageItemByObjectId(equipId, 1);
        //保存背包
        SpringContext.getItemService().saveItemStorageEnt(player);
        logger.info("已穿上[{}]装备", itemResource.getName());

        return true;
    }

    @Override
    public boolean unEquip(Player player, int position) {
        AbstractItem equipItem = player.getEquipBar().unEquip(position);
        if (equipItem == null) {
            logger.info("该部位不存在物件", EquipmentType.getEquipmentTypeId(position));
            return false;
        }
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
        equipmentManager.saveEquipStorageEnt(getEquipStorageEnt(player.getPlayerName()));

    }

    @Override
    public EquipStorageEnt getEquipStorageEnt(String accountId) {
        return equipmentManager.getEquipStorageEnt(accountId);
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
}
