package com.example.gamedemo.json;

import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.bag.model.*;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wengj
 * @description
 * @date 2019/5/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonTest {

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
}
