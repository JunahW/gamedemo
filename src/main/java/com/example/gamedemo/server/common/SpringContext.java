package com.example.gamedemo.server.common;

import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.bag.service.ItemService;
import com.example.gamedemo.server.game.equip.service.EquipmentService;
import com.example.gamedemo.server.game.player.service.PlayerService;
import com.example.gamedemo.server.game.scene.service.SceneService;
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
  private static SpringContext instance;
  @Autowired private ApplicationContext applicationContext;
  @Autowired private ItemService itemService;
  @Autowired private EquipmentService equipmentService;
  @Autowired private PlayerService playerService;
  @Autowired private AccountService accountService;
  @Autowired private SceneService sceneService;

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

  public static SceneService getSceneService() {
    return instance.sceneService;
  }

  @PostConstruct
  public void init() {
    instance = this;
  }

  @Override
  public void setApplicationContext(ApplicationContext ac) throws BeansException {
    applicationContext = ac;
  }
}
