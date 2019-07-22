package com.example.gamedemo.server.game.rank.handler;

import com.example.gamedemo.server.game.rank.constant.RankTypeEnum;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wengj
 * @description：排名处理器
 * @date 2019/7/19
 */
public abstract class AbstractRankHandler<T extends Comparable> {

  /** 处理器集合 */
  private static final Map<RankTypeEnum, AbstractRankHandler> handlerMap = new HashMap<>();
  /** 排行板 */
  private CopyOnWriteArrayList<T> rankInfos = new CopyOnWriteArrayList();

  /**
   * 获取排行处理器
   *
   * @param rankTypeEnum
   * @return
   */
  public static AbstractRankHandler getRankHandler(RankTypeEnum rankTypeEnum) {
    return handlerMap.get(rankTypeEnum);
  }

  /**
   * 获取处理器类型
   *
   * @return
   */
  public abstract RankTypeEnum getRankType();

  @PostConstruct
  public void init() {
    handlerMap.put(getRankType(), this);
  }

  /** 初始化排名 */
  public abstract void initRank();

  /**
   * 更细排名
   *
   * @param rankInfo
   */
  public abstract void updateRank(T rankInfo);

  public CopyOnWriteArrayList<T> getRankInfos() {
    return rankInfos;
  }

  public void setRankInfos(CopyOnWriteArrayList<T> rankInfos) {
    this.rankInfos = rankInfos;
  }
}
