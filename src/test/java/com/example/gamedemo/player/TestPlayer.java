package com.example.gamedemo.player;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.SystemInitializer;
import com.example.gamedemo.server.game.player.resource.PlayerResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description
 * @date 2019/5/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPlayer {


    @Test
    public void testPlayerResource() {
        SystemInitializer.initResource();
        ConcurrentMap<String, PlayerResource> itemResource = ResourceManager.getResourceMap(PlayerResource.class);
        System.out.println(itemResource);
    }

}
