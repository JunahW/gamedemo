package com.example.gamedemo.npc;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.npc.resource.NpcResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/6/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NpcTest {
  @Test
  public void testNpcResource() {
    Map<Object, NpcResource> resourceMap = ResourceManager.getResourceMap(NpcResource.class);
    System.out.println(resourceMap);
  }
}
