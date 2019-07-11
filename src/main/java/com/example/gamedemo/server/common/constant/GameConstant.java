package com.example.gamedemo.server.common.constant;

/**
 * @author: wengj
 * @date: 2019/7/2
 * @description:游戏中的常量
 */
public interface GameConstant {

  /** 视野半径 */
  int VISION_RADIUS = 4;

  /** 最小攻击力 */
  int MIN_ATTACK = 10;

  /** 百分比 */
  int PERCENTAGE = 100;

  /** 怪物重生事件 */
  long MONSTER_REBORN_PERIOD = 20000;

  /** 场景定时器延时 */
  long SCENE_DELAY = 1000;

  /** 场景定时器周期 */
  long SCENE_PERIOD = 100;
}
