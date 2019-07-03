package com.example.gamedemo.json;

import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.bag.model.*;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/5/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonTest {

  /** 测试json支持java的多态 */
  @Test
  public void testJson() {
    MedicineItem medicineItem = new MedicineItem();
    EquipItem equipItem = new EquipItem();
    CommonItem commonItem = new CommonItem();
    GemstoneItem gemstoneItem = new GemstoneItem();
    AbstractItem[] abstractItems = new AbstractItem[4];
    abstractItems[0] = medicineItem;
    abstractItems[1] = commonItem;
    abstractItems[2] = gemstoneItem;
    abstractItems[3] = equipItem;
    ItemStorage itemStorage = new ItemStorage();
    itemStorage.setAbstractItems(abstractItems);

    String s = JsonUtils.serializeEntity(itemStorage);

    ItemStorage itemStorage1 = JsonUtils.deSerializeEntity(s, ItemStorage.class);
  }

  @Test
  public void testJsonNullValue() {
    Object o = JsonUtils.deSerializeEntity("", Object.class);
    System.out.println(o);
  }

  @Test
  public void testJsonSlotAttribute() {
    EquipStorage equipStorage = new EquipStorage();
    String s = JsonUtils.serializeEntity(equipStorage);
    System.out.println(s);
  }

  @Test
  public void testMapJson() {
    Map<String, Integer> map = JsonUtils.deSerializeEntity("{\"radius\":4}", Map.class);
    Integer integer = map.get("radius");
    System.out.println(integer);
  }
}
