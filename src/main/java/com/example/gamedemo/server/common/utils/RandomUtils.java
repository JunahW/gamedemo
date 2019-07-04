package com.example.gamedemo.server.common.utils;

import com.example.gamedemo.server.common.constant.GameConstant;

import java.util.Random;

/**
 * @author wengj
 * @description
 * @date 2019/7/4
 */
public class RandomUtils {

  private static final Random random = new Random();

  /**
   * 获取两个数字之间的随机值
   *
   * @param lower
   * @param upper
   * @return
   */
  public static long getRandomNumBetween(long lower, long upper) {
    int percentage = random.nextInt(GameConstant.PERCENTAGE);
    return lower + (upper - lower) * percentage / GameConstant.PERCENTAGE;
  }

  /**
   * 通过概率给出随机boolea值
   *
   * @param chance
   * @return
   */
  public static boolean getRandomBoolean(double chance) {
    int nextInt = random.nextInt(GameConstant.PERCENTAGE);
    double value = chance * GameConstant.PERCENTAGE;
    if (value >= nextInt) {
      return true;
    }
    return false;
  }
}
