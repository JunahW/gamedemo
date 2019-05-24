package com.example.gamedemo.server.game.account.service;

import com.alibaba.fastjson.JSON;
import com.example.gamedemo.server.common.constant.SystemConstant;
import com.example.gamedemo.server.common.resource.ResourceManager;
import com.example.gamedemo.server.game.account.entity.AccountEnt;
import com.example.gamedemo.server.game.account.mapper.AccountMapper;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description: 账户业务实现层
 */
@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;


    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Override
    public Account getAccountById(String accountId) {
        logger.info("客户端查询accountId：{}", accountId);
        return AccountManager.getAccountById(accountId);
    }

    @Override
    public int setAccount(Account account) {
        Account accountById = getAccountById(account.getAcountId());
        if (null != accountById) {
            logger.info("该用户已存在");
            return 0;
        }
        logger.info("新增用户：{}", account.getAcountName());
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.setAccountId(account.getAcountId());
        accountEnt.setAccountData(JSON.toJSONString(account));
        int result = accountMapper.addAcount(accountEnt);
        if (result == 1) {
            AccountManager.setAccount(account);
            logger.info("新增用户成功");
        } else {
            logger.info("新增用户失败");
        }
        return result;
    }

    @Override
    public Account login(String accountId) {
        //先从缓存中获取
        Account account = AccountManager.getAccountById(accountId);
        AccountEnt accountEnt = null;
        if (account != null) {
            logger.info("{}登陆成功", account);
            if (account.getScene() == null) {
                //设置默认场景
                account.setScene(ResourceManager.getResourceItemById(Scene.class, SystemConstant.DEFAULT_SCENE));
            }
        } else {
            accountEnt = accountMapper.selectAccountById(accountId);
            if (accountEnt != null) {
                account = JSON.parseObject(accountEnt.getAccountData(), Account.class);
                //将登陆用户放入容器
                AccountManager.setLoginAccount(account);
                logger.info("{}登陆成功", account);
            } else {
                logger.info("{}登陆失败", accountId);
            }
        }

        return account;
    }

    @Override
    public void updateAccount(Account account) {
        logger.info("异步保存数据");
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.setAccountId(account.getAcountId());
        accountEnt.setAccountData(JSON.toJSONString(account));
        accountMapper.updateAccount(accountEnt);
    }

    @Override
    public List<Account> getAccountList() {
        List<Account> accountList = new ArrayList<>();
        List<AccountEnt> accountEntList = accountMapper.selectAccountEntList();
        for (AccountEnt accountEnt : accountEntList) {
            if (accountEnt != null) {
                Account account = JSON.parseObject(accountEnt.getAccountData(), Account.class);
                accountList.add(account);
            }
        }
        return accountList;
    }

    @Override
    public boolean move2Coordinate(Account account, int x, int y) {
        Scene scene = account.getScene();
        int[][] sceneMap = scene.getSceneMap();
        if (scene.getWidth() - 1 < x || scene.getHeight() - 1 < y) {
            logger.info("请求参数不合法");
            return false;
        }
        //修改玩家当前位置
        if (sceneMap[x][y] == 0) {
            logger.info("该位置有障碍物");
            return false;
        }

        /**
         * 移动位置
         */
        int currentx = account.getX();
        int currenty = account.getY();
        account.setX(x);
        account.setY(y);
        logger.info("({},{})从移动到({},{})", currentx, currenty, x, y);
        return true;
    }
}
