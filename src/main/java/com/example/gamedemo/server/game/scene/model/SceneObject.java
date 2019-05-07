package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.server.common.anno.ExcelColumn;

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
    @ExcelColumn(columnName = "objectId")
    private String objectId;

    /**
     * 实体名称
     */
    @ExcelColumn(columnName = "objectName")
    private String objectName;

    /**
     * 实体状态 0 死亡 1 生存
     */
    @ExcelColumn(columnName = "status")
    private int status;

    /**
     * 场景id
     */
    @ExcelColumn(columnName = "sceneId")
    private String sceneId;

    public String getObjectId() {
        return objectId;
    }

    public int getStatus() {
        return status;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getSceneId() {
        return sceneId;
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

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
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
        if (this == o) {
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
