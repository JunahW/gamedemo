package com.example.gamedemo.server.game.guild.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.guild.constant.PositionTypeEnum;
import com.example.gamedemo.server.game.guild.entity.GuildEnt;
import com.example.gamedemo.server.game.guild.entity.PlayerGuildEnt;
import com.example.gamedemo.server.game.guild.model.Guild;
import com.example.gamedemo.server.game.guild.packet.SM_ApplyList;
import com.example.gamedemo.server.game.guild.packet.SM_GuildList;
import com.example.gamedemo.server.game.guild.packet.SM_MemberList;
import com.example.gamedemo.server.game.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wengj
 * @description：公会业务层
 * @date 2019/7/18
 */
@Service
public class GuildServiceImpl implements GuildService {
  private static final Logger logger = LoggerFactory.getLogger(GuildServiceImpl.class);

  @Autowired private GuildManager guildManager;

  @Autowired private PlayerGuildManager playerGuildManager;

  /** 行会锁 */
  private ConcurrentHashMap<Long, ReentrantLock> guildLocks = new ConcurrentHashMap<>();

  /** 个人锁 */
  private ConcurrentHashMap<Long, ReentrantLock> playerLocks = new ConcurrentHashMap<>();

  @Override
  public void createGuild(Player player, Long guildId, String guildName) {
    Guild guild = guildManager.getGuildById(guildId);
    PlayerGuildEnt playerGuildEnt = playerGuildManager.getPlayerGuildEnt(player.getId());

    if (playerGuildEnt.getGuildId() != null) {
      logger.info("玩家[{}]已经加入行会[{}]无法创建行会", player.getId(), player.getGuild());
      RequestException.throwException(I18nId.PLAYER_HAS_JOIN_GUILD);
    }
    if (guild != null) {
      logger.info("该公会[{}]已经存在", guildId);
      RequestException.throwException(I18nId.GUILD_HAS_EXISTED);
    }
    ReentrantLock guildLock = getLockByGuildId(guildId);
    ReentrantLock playerLock = getLockByPlayerId(player.getId());
    try {
      // 二次验证
      if (playerGuildEnt.getGuildId() != null) {
        logger.info("玩家[{}]已经加入行会[{}]无法创建行会", player.getId(), player.getGuild());
        RequestException.throwException(I18nId.PLAYER_HAS_JOIN_GUILD);
      }
      if (guild != null) {
        logger.info("该公会[{}]已经存在", guildId);
        RequestException.throwException(I18nId.GUILD_HAS_EXISTED);
      }
      guild = Guild.valueOf(guildId, guildName, player.getId());
      guild.setPresident(player.getId());
      guild.addMember(player.getId(), PositionTypeEnum.PRESIDENT);
      guild.setId(guildId);
      GuildEnt guildEnt = GuildEnt.valueOf(guild);

      // 加玩家锁
      playerLock.lock();
      playerGuildEnt.setGuildId(guildId);
      playerGuildEnt.setPosition(PositionTypeEnum.PRESIDENT.getTypeId());
      playerGuildManager.savePlayerGuildEnt(playerGuildEnt);
      guildManager.saveGuildEnt(guildEnt);

      SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("创建公会成功"));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      playerLock.unlock();
      guildLock.unlock();
    }
  }

  @Override
  public void getGuildList(Player player) {
    List<Guild> guildList = guildManager.getGuildList();
    SM_GuildList sm_guildList = SM_GuildList.valueOf(guildList);
    SessionManager.sendMessage(player, sm_guildList);
  }

  @Override
  public void joinGuild(Player player, Long guildId) {
    Guild guild = guildManager.getGuildById(guildId);
    if (guild == null) {
      logger.info("该公会[{}]不存在", guildId);
      SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("该公会不存在"));
      RequestException.throwException(I18nId.GUILD_NO_EXIST);
    }

    PlayerGuildEnt playerGuildEnt = playerGuildManager.getPlayerGuildEnt(player.getId());
    if (playerGuildEnt != null && playerGuildEnt.getGuildId() != null) {
      logger.info("玩家[{}]已经加入行会[{}]无法创建行会", player.getId(), player.getGuild());
      RequestException.throwException(I18nId.PLAYER_HAS_JOIN_GUILD);
    }
    ReentrantLock reentrantLock = getLockByGuildId(guildId);
    ReentrantLock playerLock = getLockByPlayerId(player.getId());
    try {
      reentrantLock.lock();
      playerLock.lock();

      // 二次判断
      if (playerGuildEnt != null && playerGuildEnt.getGuildId() != null) {
        logger.info("玩家[{}]已经加入行会[{}]无法创建行会", player.getId(), player.getGuild());
        RequestException.throwException(I18nId.PLAYER_HAS_JOIN_GUILD);
      }
      guild.addApply(player.getId());
      SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("已提交申请，会长审核方可加入"));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      playerLock.unlock();
      reentrantLock.unlock();
    }
  }

  @Override
  public void handleApply(Player player, Long applyPlayerId, boolean agree) {
    PlayerGuildEnt playerGuildEnt = playerGuildManager.getPlayerGuildEnt(player.getId());
    Long guildId = playerGuildEnt.getGuildId();
    if (guildId == null) {
      logger.info("玩家[{}]还未加入公会");
      RequestException.throwException(I18nId.PLAYER_NO_JOIN_GUILD);
    }

    Guild guild = guildManager.getGuildById(guildId);
    PositionTypeEnum positionType = guild.getMemberPositionType(player.getId());
    if (positionType == null || !positionType.equals(PositionTypeEnum.PRESIDENT)) {
      logger.info("您没有权限处理");
      RequestException.throwException(I18nId.PLAYER_NO_PERMISSION);
    }

    PlayerGuildEnt applyPlayerGuildEnt = playerGuildManager.getPlayerGuildEnt(applyPlayerId);
    if (applyPlayerGuildEnt != null && applyPlayerGuildEnt.getGuildId() != null) {
      logger.info(
          "玩家[{}]已经加入行会[{}]无法创建行会",
          applyPlayerGuildEnt.getPlayerId(),
          applyPlayerGuildEnt.getGuildId());
      RequestException.throwException(I18nId.PLAYER_HAS_JOIN_GUILD);
    }

    ReentrantLock guildLock = getLockByGuildId(guildId);
    ReentrantLock playerLock = getLockByPlayerId(applyPlayerId);
    try {
      guildLock.lock();

      if (!agree) {
        guild.removeApply(applyPlayerId);
        SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("已拒绝加入"));
        return;
      }
      // 玩家锁
      playerLock.lock();

      if (applyPlayerGuildEnt != null && applyPlayerGuildEnt.getGuildId() != null) {
        logger.info(
            "玩家[{}]已经加入行会[{}]无法创建行会",
            applyPlayerGuildEnt.getPlayerId(),
            applyPlayerGuildEnt.getGuildId());
        RequestException.throwException(I18nId.PLAYER_HAS_JOIN_GUILD);
      }
      guild.addMember(applyPlayerId, PositionTypeEnum.MEMBER);
      guild.removeApply(applyPlayerId);
      // 玩家锁
      playerLock.lock();
      applyPlayerGuildEnt.setGuildId(guildId);
      applyPlayerGuildEnt.setPosition(PositionTypeEnum.MEMBER.getTypeId());
      playerGuildManager.savePlayerGuildEnt(applyPlayerGuildEnt);
      SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("已同意加入"));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      playerLock.unlock();
      guildLock.unlock();
    }
  }

  @Override
  public void quitGuild(Player player) {
    // 加锁
    PlayerGuildEnt playerGuildEnt = playerGuildManager.getPlayerGuildEnt(player.getId());
    Long guildId = playerGuildEnt.getGuildId();
    if (playerGuildEnt.getGuildId() == null) {
      logger.info("还未加入公会");
      RequestException.throwException(I18nId.PLAYER_NO_JOIN_GUILD);
    }
    ReentrantLock reentrantLock = getLockByGuildId(guildId);
    ReentrantLock playerLock = getLockByPlayerId(player.getId());

    try {
      Guild guild = guildManager.getGuildById(guildId);
      // 退出公会
      reentrantLock.lock();
      playerLock.lock();
      // 二次校验
      if (playerGuildEnt.getGuildId() == null) {
        logger.info("还未加入公会");
        RequestException.throwException(I18nId.PLAYER_NO_JOIN_GUILD);
      }

      playerGuildEnt.setGuildId(null);
      playerGuildEnt.setPosition(null);
      guild.removeMember(player.getId());
      guildManager.saveGuildEnt(GuildEnt.valueOf(guild));
      playerGuildManager.savePlayerGuildEnt(playerGuildEnt);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      playerLock.unlock();
      reentrantLock.unlock();
    }
  }

  @Override
  public void showApply(Player player) {
    PlayerGuildEnt playerGuildEnt = playerGuildManager.getPlayerGuildEnt(player.getId());
    Long guildId = playerGuildEnt.getGuildId();
    Guild guild = guildManager.getGuildById(guildId);
    PositionTypeEnum positionType = guild.getMemberPositionType(player.getId());
    if (!positionType.equals(PositionTypeEnum.PRESIDENT)) {
      logger.info("您不是会长，没有该权限");
      SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("您不是会长，没有改权限"));
      return;
    }
    SessionManager.sendMessage(player, SM_ApplyList.valueOf(guild.getApplySet()));
  }

  @Override
  public void showMembers(Player player) {
    PlayerGuildEnt playerGuildEnt = playerGuildManager.getPlayerGuildEnt(player.getId());
    Long guildId = playerGuildEnt.getGuildId();
    if (guildId == null) {
      logger.info("玩家[{}]还未加入公会");
      RequestException.throwException(I18nId.PLAYER_NO_JOIN_GUILD);
    }
    Guild guild = guildManager.getGuildById(guildId);
    Map<Long, PositionTypeEnum> members = guild.getMembers();
    SessionManager.sendMessage(player, SM_MemberList.valueOf(members));
  }

  /**
   * 获取对应的锁
   *
   * @param guildId
   * @return
   */
  private ReentrantLock getLockByGuildId(Long guildId) {
    if (guildLocks.get(guildId) == null) {
      guildLocks.putIfAbsent(guildId, new ReentrantLock());
    }
    return guildLocks.get(guildId);
  }

  /**
   * 获取个人锁
   *
   * @param playerId
   * @return
   */
  private ReentrantLock getLockByPlayerId(Long playerId) {
    if (playerLocks.get(playerId) == null) {
      playerLocks.putIfAbsent(playerId, new ReentrantLock());
    }
    return playerLocks.get(playerId);
  }
}
