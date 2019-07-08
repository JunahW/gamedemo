package com.example.gamedemo.server.common.utils;

/**
 * @author wengj
 * @description:公式工具类
 * @date 2019/6/13
 */
public class FormulaUtils {

  /**
   * 计算两个坐标的距离
   *
   * @param x
   * @param y
   * @param targetX
   * @param targetY
   * @return
   */
  public static int computeDistance(int x, int y, int targetX, int targetY) {
    int distanceSquare = Math.abs(x * x - targetX * targetX) + Math.abs(y * y + targetY * targetY);
    return (int) Math.sqrt(distanceSquare);
  }
}
