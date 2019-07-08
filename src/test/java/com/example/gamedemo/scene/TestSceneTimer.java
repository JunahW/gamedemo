package com.example.gamedemo.scene;

import com.example.gamedemo.common.executer.scene.SceneExecutor;
import com.example.gamedemo.server.game.scene.command.AbstractSceneBuffTriggerCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wengj
 * @description
 * @date 2019/7/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSceneTimer {
  @Test
  public void testTimer() {
    SceneExecutor.addScheduleTask(
        2001, 0, 100, Long.MAX_VALUE, AbstractSceneBuffTriggerCommand.valueOf(2001));
    while (true) {}
  }
}
