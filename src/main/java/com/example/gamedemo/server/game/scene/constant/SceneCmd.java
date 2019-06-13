package com.example.gamedemo.server.game.scene.constant;

/**
 * @author wengj
 * @description 场景指令
 * @date 2019/4/28
 */
public interface SceneCmd {

  /** 展示地图 */
  String LIST = "list";

  /** 进入地图 */
  String GOTO = "goto";

  /** 进入相邻场景 */
  String MOVE = "move";

  /** 查看当前场景的所有对象 */
  String AOI = "aoi";
}
