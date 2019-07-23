package com.example.gamedemo.task;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.task.model.Task;
import com.example.gamedemo.server.game.task.resource.TaskResource;
import com.example.gamedemo.server.game.task.storage.TaskStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/7/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTest {

  @Test
  public void testResource() {
    Map<Object, TaskResource> resourceMap = ResourceManager.getResourceMap(TaskResource.class);
    System.out.println(resourceMap);
  }

  @Test
  public void testJson() {
    TaskStorage taskStorage = new TaskStorage();
    taskStorage.putExecuteTask(Task.valueOf(1));
    taskStorage.addFinishTask(2);
    System.out.println(taskStorage);
  }
}
