package com.example.gamedemo.server.game.account.mapper;

import com.example.gamedemo.server.game.account.entity.AccountEnt;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    AccountEnt selectAccountById(String accountId);

    void addAcount(AccountEnt accountEnt);
}
