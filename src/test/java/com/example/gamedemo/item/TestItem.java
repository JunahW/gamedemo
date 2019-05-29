package com.example.gamedemo.item;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.SystemInitializer;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.bag.service.ItemManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description
 * @date 2019/5/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestItem {

    @Autowired
    private ItemManager itemManager;

    @Test
    public void testItemResource() {
        SystemInitializer.initResource();
        ConcurrentMap<String, ItemResource> itemResource = ResourceManager.getResourceMap(ItemResource.class);
        System.out.println(itemResource);
    }

    @Test
    public void testManager() {
        System.out.println(itemManager.getItemStorageEnt("8521"));
        System.out.println(itemManager.getItemStorageEnt("8521"));
    }
}
