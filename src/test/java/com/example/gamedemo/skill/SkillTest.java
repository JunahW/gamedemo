package com.example.gamedemo.skill;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.skill.resource.SkillResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/6/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillTest {

  @Test
  public void testSkillResource() {
    Map<Object, SkillResource> resourceMap = ResourceManager.getResourceMap(SkillResource.class);
    System.out.println(resourceMap);
  }
}
