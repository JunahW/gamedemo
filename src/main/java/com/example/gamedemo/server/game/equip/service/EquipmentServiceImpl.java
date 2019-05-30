package com.example.gamedemo.server.game.equip.service;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.model.EquipItem;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wengj
 * @description：装备业务层
 * @date 2019/5/30
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentManager equipmentManager;

    /*@Autowired
    private ItemManager itemManager;*/


    @Override
    public boolean equip(Account account, long equipId) {
        AbstractItem item = account.getPack().getStorageItemByObjectId(equipId);
        String itemResourceId = item.getItemResourceId();
        //ItemResource resourceById = itemManager.getResourceById(itemResourceId);


        return false;
    }

    @Override
    public boolean unEquip(Account account, long equipId) {
        return false;
    }

    @Override
    public EquipItem getEquipItemByGuid(Account account, long guid) {
        return null;
    }

    @Override
    public void saveEquipmentStorageEnt(Account account) {

    }

    @Override
    public EquipStorageEnt getEquipStorageEnt(String accountId) {
        return equipmentManager.getEquipStorageEnt(accountId);
    }
}
