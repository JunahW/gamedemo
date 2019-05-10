package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.server.common.anno.ExcelColumn;

import java.util.Arrays;

/**
 * @author wengj
 * @description 地形
 * @date 2019/5/10
 */
public class Map {
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
}
