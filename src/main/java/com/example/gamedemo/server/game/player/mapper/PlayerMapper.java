package com.example.gamedemo.server.game.player.mapper;

import com.example.gamedemo.server.game.player.entity.PlayerEnt;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerMapper {
    PlayerEnt selectAccountById(String accountId);

    int addAcount(PlayerEnt playerEnt);

    int updateAccount(PlayerEnt playerEnt);

    List<PlayerEnt> selectAccountEntList();
}
