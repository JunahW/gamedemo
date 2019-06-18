package com.example.gamedemo.Equip;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.model.CommonItem;
import com.example.gamedemo.server.game.bag.model.EquipItem;
import com.example.gamedemo.server.game.bag.model.GemstoneItem;
import com.example.gamedemo.server.game.bag.service.ItemManager;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.equip.resource.EquipAttrResource;
import com.example.gamedemo.server.game.equip.resource.EquipEnhanceResource;
import com.example.gamedemo.server.game.equip.service.EquipmentManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/5/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EquipTest {

  @Autowired private ItemManager itemManager;

  @Autowired private EquipmentManager equipmentManager;

  /** 测试json是否支持多态 */
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
    ResourceManager.initResource();
    Map<Object, EquipAttrResource> resourceMap =
        ResourceManager.getResourceMap(EquipAttrResource.class);
    Map<Object, EquipAttrResource> resourceMap1 =
        ResourceManager.getResourceMap(EquipAttrResource.class);

    System.out.println(resourceMap);
  }

  @Test
  public void testEquipEnhanceResource() {
    ResourceManager.initResource();
    Map<Object, EquipEnhanceResource> resourceMap =
        ResourceManager.getResourceMap(EquipEnhanceResource.class);
    System.out.println(resourceMap);
  }

  @Test
  public void testEquipManager() {
    System.out.println(equipmentManager);
  }
}
