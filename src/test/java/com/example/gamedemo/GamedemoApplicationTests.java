package com.example.gamedemo;

import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.manager.ControllerManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GamedemoApplicationTests {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ControllerManager controllerManager;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAccout() {
        System.out.println(accountService);
    }

    @Test
    public void testController() {
        System.out.println(controllerManager);
    }

}
