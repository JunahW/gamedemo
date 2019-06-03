package com.example.gamedemo.attribute;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.SystemInitializer;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.equip.resource.EquipAttrResource;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

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
        String jsonString = "[\n" +
                "    {\n" +
                "        \"type\": \"String01\",\n" +
                "        \"value\": 555\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": \"String01\",\n" +
                "        \"value\": 555\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": \"String01\",\n" +
                "        \"value\": 555\n" +
                "    }\n" +
                "]";
        List<Attribute> listByString = JsonUtils.getListByString(jsonString, new TypeReference<List<Attribute>>() {
        });
        System.out.println(listByString);

    }

    @Test
    public void testEquipmentAttrResource() {
        SystemInitializer.initResource();
        ConcurrentMap<Object, EquipAttrResource> resourceMap = ResourceManager.getResourceMap(EquipAttrResource.class);
        EquipAttrResource equipAttrResource = resourceMap.get("i1003");
        List<Attribute> attributes = equipAttrResource.getAttributes();
        System.out.println(attributes);

    }
}
