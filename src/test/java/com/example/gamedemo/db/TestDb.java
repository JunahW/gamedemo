package com.example.gamedemo.db;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.server.game.player.entity.PlayerEnt;
import com.example.gamedemo.server.game.player.service.PlayerManager;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/5/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDb {

  @Autowired private SessionFactory sessionFactory;

  @Autowired private Accessor accessor;

  @Autowired private PlayerManager playerManager;

  @Test
  public void tsetDb() {
    System.out.println("==========");
    System.out.println(sessionFactory);
  }

  /** 测试持久层 */
  @Test
  public void testAccessor() {
    EntityCacheServiceImpl<Long, PlayerEnt> service = new EntityCacheServiceImpl<>();
    service.setClazz(PlayerEnt.class);
    service.setAccessor(accessor);
    List<PlayerEnt> entityList = service.getEntityList();
    System.out.println(entityList);
  }
}
