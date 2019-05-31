package com.example.gamedemo.db;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.server.game.account.service.AccountService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wengj
 * @description
 * @date 2019/5/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private Accessor accessor;



}
