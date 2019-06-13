package com.example.gamedemo.account;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerTest {

  @Test
  public void testAccountSerializable() throws Exception {
    Player player = new Player();
    player.setPlayerId("t101");
    player.setPlayerName("t噬魂");
    SceneResource sceneResource = new SceneResource();
    sceneResource.setSceneId("s101");
    sceneResource.setSceneName("s村庄");
    // player.setScene(scene);

  }

  @Test
  public void testArray() {}
}
