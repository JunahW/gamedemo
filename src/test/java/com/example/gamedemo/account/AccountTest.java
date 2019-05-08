package com.example.gamedemo.account;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountTest {

    @Test
    public void testAccountSerializable() throws Exception {
        Account account = new Account();
        account.setAcountId("t101");
        account.setAcountName("t噬魂");
        Scene scene = new Scene();
        scene.setSceneId("s101");
        scene.setSceneName("s村庄");
        //account.setScene(scene);

    }
}
