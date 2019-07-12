package com.example.gamedemo.client.handler;

import com.example.gamedemo.client.utils.JsonFormatterUtils;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.vo.SceneObjectVo;
import com.example.gamedemo.server.game.scene.packet.SM_Aoi;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/7/12
 */
public class SceneHandler {

  public void handAoiMessage(Object message) {
    SM_Aoi smAoi = (SM_Aoi) message;
    int[][] mapArray = smAoi.getMapArray();
    // 自己的坐标
    mapArray[smAoi.getX()][smAoi.getY()] = -1;
    List<SceneObjectVo> sceneObjectVoList = smAoi.getSceneObjectVoList();
    for (SceneObjectVo sceneObjectVo : sceneObjectVoList) {
      int x = sceneObjectVo.getX();
      int y = sceneObjectVo.getY();
      if (sceneObjectVo.getSceneObjectTypeEnum().equals(SceneObjectTypeEnum.PLAYER)) {
        mapArray[x][y] = 2;
      } else if (sceneObjectVo.getSceneObjectTypeEnum().equals(SceneObjectTypeEnum.MONSTER)) {
        mapArray[x][y] = 3;
      } else if (sceneObjectVo.getSceneObjectTypeEnum().equals(SceneObjectTypeEnum.NPC)) {
        mapArray[x][y] = 4;
      } else if (sceneObjectVo.getSceneObjectTypeEnum().equals(SceneObjectTypeEnum.DROP_OBJECT)) {
        mapArray[x][y] = 5;
      }
    }
    for (int[] xArray : mapArray) {
      for (int y : xArray) {
        if (y == 0) {
          System.out.print("#");
        } else if (y == 1) {
          System.out.print("*");
        } else if (y == 2) {
          System.out.print("p");
        } else if (y == 3) {
          System.out.print("m");
        } else if (y == 4) {
          System.out.print("n");
        } else if (y == 5) {
          System.out.print("d");
        } else if (y == -1) {
          System.out.print("@");
        }
      }
      System.out.println();
    }
    JsonFormatterUtils.printObjectJsonString(smAoi.getSceneObjectVoList());
  }
}
