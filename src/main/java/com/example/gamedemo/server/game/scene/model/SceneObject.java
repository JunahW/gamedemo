package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.resource.ResourceInterface;

import java.util.Objects;

/**
 * @author wengj
 * @description 场景实体的父类
 * @date 2019/5/5
 */
public class SceneObject implements ResourceInterface {

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
     * 所在的位置x值
     */
    @ExcelColumn(columnName = "x")
    private int x;

    /**
     * 所在的位置y值
     */
    @ExcelColumn(columnName = "y")
    private int y;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "SceneObject{" +
                "objectId='" + objectId + '\'' +
                ", objectName='" + objectName + '\'' +
                ", status=" + status +
                ", x=" + x +
                ", y=" + y +
                ", sceneId='" + sceneId + '\'' +
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

    @Override
    public Object getId() {
        return this.objectId;
    }

    @Override
    public void postInit() {

    }
}
