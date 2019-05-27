package com.example.gamedemo.db;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author wengj
 * @description
 * @date 2019/5/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDb {


    @Resource
    private SessionFactory sessionFactory;
    @Test
    public void tsetDb() {
        System.out.println("==========");
        System.out.println(sessionFactory);
    }
}
