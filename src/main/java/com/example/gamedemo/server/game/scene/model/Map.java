package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

import java.util.Arrays;

/**
 * @author wengj
 * @description 地形
 * @date 2019/5/10
 */
@Resource
public class Map implements ResourceInterface {
    /**
     * 地图id
     */
    @ExcelColumn(columnName = "mapId")
    private String mapId;

    /**
     * 地图的宽
     */
    @ExcelColumn(columnName = "width")
    private int width;

    /**
     * 地图的高
     */
    @ExcelColumn(columnName = "height")
    private int height;

    /**
     * 出生地坐标x
     */
    @ExcelColumn(columnName = "x")
    private int x;

    /**
     * 出生地坐标y
     */
    @ExcelColumn(columnName = "y")
    private int y;


    /**
     * 地图数组字符串
     */
    @ExcelColumn(columnName = "sceneMap")
    private int[][] sceneMap;


    public String getMapId() {
        return mapId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getSceneMap() {
        return sceneMap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSceneMap(int[][] sceneMap) {
        this.sceneMap = sceneMap;
    }

    @Override
    public String toString() {
        return "Map{" +
                "mapId='" + mapId + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", sceneMap=" + Arrays.toString(sceneMap) +
                '}';
    }

    @Override
    public Object getId() {
        return this.mapId;
    }
}
