package com.example.gamedemo.server.game.scene.resource;

import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.server.game.scene.model.SceneObject;

/**
 * @author wengj
 * @description
 * @date 2019/5/5
 */
@Resource
public class NpcResource extends SceneObject {
    public NpcResource(String objectId, String objectName, int status) {
        super(objectId, objectName, status);
    }

    public NpcResource() {
    }


}
