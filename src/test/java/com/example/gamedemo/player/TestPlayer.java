package com.example.gamedemo.player;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.player.entity.PlayerEnt;
import com.example.gamedemo.server.game.player.resource.PlayerResource;
import com.example.gamedemo.server.game.player.service.PlayerManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TestPlayer {

  @Autowired private PlayerManager playerManager;

  @Test
  public void testPlayerResource() {
    ResourceManager.initResource();
    Map<String, PlayerResource> itemResource = ResourceManager.getResourceMap(PlayerResource.class);
    System.out.println(itemResource);
  }

  @Test
  public void testGetPlayer() {
    PlayerEnt playerEntByPlayerId = playerManager.getPlayerEntByPlayerId(1000L);
    System.out.println(playerEntByPlayerId.getPlayer());
  }
}
