package com.example.gamedemo;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.bag.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

  @Test
  public void testHashMap() {
    Map<Integer, Integer> hashMap = new HashMap<>();
    hashMap.put(1, 1);
    hashMap.put(2, 1);
    hashMap.put(3, 1);
    hashMap.put(4, 1);
    hashMap.put(5, 1);
    Set<Map.Entry<Integer, Integer>> entries = hashMap.entrySet();
    for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
      // hashMap.put(6, 6);
      hashMap.remove(5);
    }
  }
}
