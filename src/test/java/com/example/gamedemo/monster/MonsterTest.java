package com.example.gamedemo.monster;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description
 * @date 2019/6/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MonsterTest {

  @Test
  public void testResource() {
    ConcurrentMap<Object, MonsterResource> resourceMap =
        ResourceManager.getResourceMap(MonsterResource.class);
    System.out.println(resourceMap);
  }
}
