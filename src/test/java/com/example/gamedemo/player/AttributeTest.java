package com.example.gamedemo.player;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.player.resource.BaseAttributeResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
  public void testAttributeResource() {
    ResourceManager.initResource();
    Map<Object, BaseAttributeResource> resourceMap =
        ResourceManager.getResourceMap(BaseAttributeResource.class);
    System.out.println(resourceMap);
  }
}
