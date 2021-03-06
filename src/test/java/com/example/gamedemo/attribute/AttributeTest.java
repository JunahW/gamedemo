package com.example.gamedemo.attribute;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.equip.resource.EquipAttrResource;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/6/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttributeTest {

  @Test
  public void testJson2Array() {
    String jsonString =
        "[\n"
            + "    {\n"
            + "        \"type\": \"String01\",\n"
            + "        \"value\": 555\n"
            + "    },\n"
            + "    {\n"
            + "        \"type\": \"String01\",\n"
            + "        \"value\": 555\n"
            + "    },\n"
            + "    {\n"
            + "        \"type\": \"String01\",\n"
            + "        \"value\": 555\n"
            + "    }\n"
            + "]";
    List<Attribute> listByString =
        JsonUtils.getListByString(jsonString, new TypeReference<List<Attribute>>() {});
    System.out.println(listByString);
  }

  @Test
  public void testEquipmentAttrResource() {
    ResourceManager.initResource();
    Map<Object, EquipAttrResource> resourceMap =
        ResourceManager.getResourceMap(EquipAttrResource.class);
    EquipAttrResource equipAttrResource = resourceMap.get("i1003");
    List<Attribute> attributes = equipAttrResource.getAttributes();
    System.out.println(attributes);
  }

  @Test
  public void testHashMapCleatFunction() {
    String s0 = "000";
    String s1 = "111";
    String s2 = "222";

    HashMap<String, String> map = new HashMap<>();
    map.put(s0, s0);
    map.put(s1, s1);
    map.put(s2, s2);
    map.clear();
    System.out.println(map);
  }
}
