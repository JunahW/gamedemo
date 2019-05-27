package com.example.gamedemo.db;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.server.game.account.entity.Player;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wengj
 * @description
 * @date 2019/5/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDb {


    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Accessor accessor;

    @Test
    public void tsetDb() {
        System.out.println("==========");
        System.out.println(sessionFactory);
    }


    /**
     * 测试持久层
     */
    @Test
    public void testAccessor() {

        accessor.save(Player.class, new Player());
    }
}
