package com.example.gamedemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.gamedemo.server.game.account.entity.AccountEnt;
import com.example.gamedemo.server.game.account.mapper.AccountMapper;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.manager.ControllerManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GamedemoApplicationTests {
    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testWriteObject() {
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.setAccountId("a1007");
        Account account = new Account();
        account.setCountId("a1007");
        account.setCountName("test07");
        String accountData = JSON.toJSONString(account);
        accountEnt.setAccountData(accountData);
        accountMapper.addAcount(accountEnt);

    }

    @Test
    public void readObject() {
        AccountEnt accountEnt = accountMapper.selectAccountById("a1007");
        Account account = JSON.parseObject(accountEnt.getAccountData(), Account.class);
        System.out.println(account);
    }


}
