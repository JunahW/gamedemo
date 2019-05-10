package com.example.gamedemo;

import com.alibaba.fastjson.JSON;
import com.example.gamedemo.server.common.anno.HandlerClass;
import com.example.gamedemo.server.common.anno.HandlerMethod;
import com.example.gamedemo.server.common.dispatcher.ControllerManager;
import com.example.gamedemo.server.common.dispatcher.InvokeMethod;
import com.example.gamedemo.server.game.account.entity.AccountEnt;
import com.example.gamedemo.server.game.account.mapper.AccountMapper;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.role.service.RoleService;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GamedemoApplicationTests {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testWriteObject() {
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.setAccountId("a1007");
        Account account = new Account();
        account.setAcountId("a1007");
        account.setAcountName("test07");
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

    @Test
    public void testSingle() throws Exception {
        roleService.saveRole(null);
        Thread.sleep(6000);
    }

    @Test
    public void testClass() {
        Class<Scene> sceneClass = Scene.class;
        System.out.println(sceneClass.getSimpleName());
    }

    @Test
    public void testApplication() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(HandlerClass.class);
        Set<Map.Entry<String, Object>> entries = beansWithAnnotation.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Class<?> aClass = entry.getValue().getClass();
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(HandlerMethod.class)) {
                    HandlerMethod annotation = method.getAnnotation(HandlerMethod.class);
                    String cmd = annotation.cmd();
                    InvokeMethod invokeMethod = new InvokeMethod(entry.getValue(), method);
                    ControllerManager.add(cmd, invokeMethod);
                }
            }
        }
    }

    @Test
    public void testObjectSize() {
        LinkedList<Account> accounts = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            Account account = new Account();
            account.setAcountId("54654546");
            account.setAcountName("5465465的店了解到");
            account.setScene(new Scene("555"));
            accounts.add(account);
        }
        System.out.println();
    }

    @Test
    public void testArray() {
        int[][] a = {{1, 1}, {2, 2}};
        System.out.println(a);
    }

}
