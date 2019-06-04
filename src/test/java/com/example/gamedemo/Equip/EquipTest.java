package com.example.gamedemo.Equip;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.SystemInitializer;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.model.CommonItem;
import com.example.gamedemo.server.game.bag.model.EquipItem;
import com.example.gamedemo.server.game.bag.model.GemstoneItem;
import com.example.gamedemo.server.game.bag.service.ItemManager;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.equip.resource.EquipAttrResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description
 * @date 2019/5/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EquipTest {

    @Autowired
    private ItemManager itemManager;

    /**
     * 测试json是否支持多态
     */
    @Test
    public void testFastJson() {
        ItemStorageEnt itemStorageEnt = itemManager.getItemStorageEnt("a1007");
        ItemStorage itemStorage = itemStorageEnt.getItemStorage();
        AbstractItem[] abstractItems = itemStorage.getAbstractItems();
        EquipItem equipItem = (EquipItem) abstractItems[1];
        System.out.println(equipItem);

    }

    @Test
    public void testArray() {
        AbstractItem[] abstractItems = new AbstractItem[3];
        abstractItems[0] = new EquipItem();
        abstractItems[1] = new CommonItem();
        abstractItems[2] = new GemstoneItem();
        System.out.println(abstractItems);
    }

    @Test
    public void testEquipmentJson() {
        SystemInitializer.initResource();
        ConcurrentMap<Object, EquipAttrResource> resourceMap = ResourceManager.getResourceMap(EquipAttrResource.class);
        System.out.println(resourceMap);
    }

}
