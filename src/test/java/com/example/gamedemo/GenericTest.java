package com.example.gamedemo;

import com.example.gamedemo.common.ramcache.service.EntityCacheService;
import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.bag.service.ItemService;
import com.example.gamedemo.server.game.role.entity.RoleEnt;
import com.example.gamedemo.server.game.role.entity.RolePlusEnt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private EntityCacheService<String, RoleEnt> entEntityCacheService;
    @Autowired
    private EntityCacheService<String, RolePlusEnt> ent2EntityCacheService;

    @Test
    public void testGeneric() {
        System.out.println("=====================");
        System.out.println(entEntityCacheService);
        System.out.println("======================");
        System.out.println(ent2EntityCacheService);
    }

    @Test
    public void getInstance() {
        ItemService itemService = SpringContext.getItemService();
        System.out.println(itemService);
    }

}
