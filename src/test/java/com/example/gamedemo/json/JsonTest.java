package com.example.gamedemo.json;

import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.bag.model.*;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.example.gamedemo.server.game.player.model.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

  @Test
  public void testMapJsonTranslate() {
    Map<Integer, Player> map = new HashMap<>();
    map.put(1, new Player());
    String s = JsonUtils.serializeEntity(map);
    Map<Integer, Player> map1 = JsonUtils.deSerializeMap(Map.class, Integer.class, Player.class, s);
    Player player = map1.get(1);
    System.out.println(player);
    System.out.println(map1);
  }

  @Test
  public void tesSetJsonTranslate() {
    TreeSet<Long> longs = new TreeSet<>();
    longs.add(564456L);
    String s = JsonUtils.serializeEntity(longs);
    Set set = JsonUtils.deSerializeSet(Set.class, Long.class, s);
    System.out.println(set);
  }
}
