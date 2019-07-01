package com.example.gamedemo.buff;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BuffTest {

  @Test
  public void testBuffResource() {
    Map<Integer, BuffResource> buffResource = ResourceManager.getResourceMap(BuffResource.class);
    System.out.println(buffResource);
  }
}
