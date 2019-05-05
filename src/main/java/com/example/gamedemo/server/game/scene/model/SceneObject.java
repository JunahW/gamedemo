package com.example.gamedemo.server.game.scene.model;

import java.util.Objects;

/**
 * @author wengj
 * @description 场景实体的父类
 * @date 2019/5/5
 */
public class SceneObject {

    /**
     * 实体id
     */
    private String objectId;

    /**
     * 实体名称
     */
    private String objectName;

    /**
     * 实体状态 0 死亡 1 生存
     */
    private int status;

    public String getObjectId() {
        return objectId;
    }

    public int getStatus() {
        return status;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public String toString() {
        return "SceneObject{" +
                "objectId='" + objectId + '\'' +
                ", objectName='" + objectName + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SceneObject that = (SceneObject) o;
        return Objects.equals(objectId, that.objectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId);
    }

    public SceneObject() {
    }

    public SceneObject(String objectId, String objectName, int status) {
        this.objectId = objectId;
        this.objectName = objectName;
        this.status = status;
    }
}
