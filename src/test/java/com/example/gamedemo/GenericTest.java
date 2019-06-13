package com.example.gamedemo;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.bag.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wengj
 * @description
 * @date 2019/5/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenericTest {

  @Test
  public void getInstance() {
    ItemService itemService = SpringContext.getItemService();
    System.out.println(itemService);
  }
}
