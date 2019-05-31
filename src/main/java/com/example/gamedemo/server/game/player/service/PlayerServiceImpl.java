package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.player.entity.PlayerEnt;
import com.example.gamedemo.server.game.player.mapper.PlayerMapper;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private Accessor accessor;


    @Autowired
    private PlayerManager playerManager;

    private static final ObjectMapper mapper = new ObjectMapper();


    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);


    @Override
    public Player getAccountById(String accountId) {
        logger.info("客户端查询accountId：{}", accountId);
        return PlayerManager.getAccountById(accountId);
    }

    @Override
    public int createPlayer(Player player) {
        PlayerEnt load = accessor.load(PlayerEnt.class, player.getPlayerId());
        if (null != load) {
            logger.info("玩家已存在");
            return 0;
        }
        player.setPlayerName(playerManager.getPlayerResourceById(player.getPlayerType()).getPlayerName());
        PlayerEnt playerEnt = new PlayerEnt();
        playerEnt.setPlayer(player);
        //TODO scene 为null是否可以装换为json？？
        playerEnt.doSerialize();
        logger.info("新增用户：{}", player.getPlayerName());

        String save = accessor.save(PlayerEnt.class, playerEnt);
        if (save != null) {
            PlayerManager.setAccount(player);
            logger.info("新增用户成功");
            return 1;
        } else {
            logger.info("新增用户失败");
            return 0;
        }

    }

    @Override
    public Player selectPlayer(String playerId) {
        PlayerEnt playerEnt = accessor.load(PlayerEnt.class, playerId);
        if (playerEnt != null) {
            playerEnt.doDeSerialize();
            Player player = playerEnt.getPlayer();
            logger.info("{}选择角色成功", playerEnt.getPlayerName());
            if (player.getScene() == null) {
                //设置默认场景
                player.setScene(ResourceManager.getResourceItemById(Scene.class, SystemConstant.DEFAULT_SCENE));
            }
            return player;
        } else {
            logger.warn("{}选择角色失败", playerId);
        }
        return null;
    }

    @Override
    public void updateAccount(Player player) {
        logger.info("异步保存数据");
        PlayerEnt playerEnt = new PlayerEnt();
        playerEnt.setPlayer(player);
        playerEnt.doSerialize();
        accessor.saveOrUpdate(PlayerEnt.class, playerEnt);
    }

    @Override
    public List<Player> getAccountList() {
        List<Player> playerList = new ArrayList<>();
       /* List<PlayerEnt> playerEntList = playerMapper.selectAccountEntList();
        for (PlayerEnt playerEnt : playerEntList) {
            if (playerEnt != null) {
                Player player = null;
                try {
                    player = mapper.readValue(playerEnt.getAccountData(), Player.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                playerList.add(player);
            }
        }*/
        return playerList;
    }

    @Override
    public boolean move2Coordinate(Player player, int x, int y) {
        Scene scene = player.getScene();
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
        int currentx = player.getX();
        int currenty = player.getY();
        player.setX(x);
        player.setY(y);
        logger.info("({},{})从移动到({},{})", currentx, currenty, x, y);
        return true;
    }
}