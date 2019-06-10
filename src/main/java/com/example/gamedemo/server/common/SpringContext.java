package com.example.gamedemo.server.common;

import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.bag.service.ItemService;
import com.example.gamedemo.server.game.equip.service.EquipmentService;
import com.example.gamedemo.server.game.player.service.PlayerService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wengj
 * @description
 * @date 2019/5/29
 */
@Component
public class SpringContext implements ApplicationContextAware {
    @Autowired
    private ApplicationContext applicationContext;

    private static SpringContext instance;

    @PostConstruct
    public void init() {
        instance = this;
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    @Autowired
    private ItemService itemService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private AccountService accountService;

    public static ItemService getItemService() {
        return instance.itemService;
    }

    public static EquipmentService getEquipmentService() {
        return instance.equipmentService;
    }

    public static PlayerService getPlayerService() {
        return instance.playerService;
    }

    public static AccountService getAccountService() {
        return instance.accountService;
    }
}
