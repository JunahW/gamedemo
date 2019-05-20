package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.server.common.anno.Resource;

/**
 * @author wengj
 * @description
 * @date 2019/5/5
 */
@Resource
public class Npc extends SceneObject {
    public Npc(String objectId, String objectName, int status) {
        super(objectId, objectName, status);
    }

    public Npc() {
    }


}
