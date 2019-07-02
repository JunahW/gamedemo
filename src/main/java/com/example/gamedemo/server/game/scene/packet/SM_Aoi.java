package com.example.gamedemo.server.game.scene.packet;

import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.base.model.SceneObjectView;
import com.example.gamedemo.server.game.base.vo.SceneObjectVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wengj
 * @description
 * @date 2019/7/2
 */
public class SM_Aoi {
  private List<SceneObjectVo> sceneObjectVoList;

  public static SM_Aoi valueOf(SceneObjectView sceneObjectView) {
    SM_Aoi sm_aoi = new SM_Aoi();
    ArrayList<SceneObjectVo> sceneObjectVos = new ArrayList<>();
    Set<Map.Entry<Long, SceneObject>> entries = sceneObjectView.getSceneObjectMap().entrySet();
    for (Map.Entry<Long, SceneObject> sceneObjectEntry : entries) {
      SceneObject value = sceneObjectEntry.getValue();
      SceneObjectVo sceneObjectVo =
          SceneObjectVo.valueOf(
              value.getId(), value.getX(), value.getY(), value.getSceneObjectType());
      sceneObjectVos.add(sceneObjectVo);
    }
    sm_aoi.setSceneObjectVoList(sceneObjectVos);
    return sm_aoi;
  }

  public List<SceneObjectVo> getSceneObjectVoList() {
    return sceneObjectVoList;
  }

  public void setSceneObjectVoList(List<SceneObjectVo> sceneObjectVoList) {
    this.sceneObjectVoList = sceneObjectVoList;
  }
}
