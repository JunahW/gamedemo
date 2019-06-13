package com.example.gamedemo.server.game.account.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.server.game.account.entity.AccountEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
@Component
public class AccountManager {

  @Autowired private Accessor accessor;

  private EntityCacheServiceImpl<String, AccountEnt> entEntityCacheService =
      new EntityCacheServiceImpl<>();

  @PostConstruct
  public void init() {
    entEntityCacheService.setClazz(AccountEnt.class);
    entEntityCacheService.setAccessor(accessor);
  }
}
