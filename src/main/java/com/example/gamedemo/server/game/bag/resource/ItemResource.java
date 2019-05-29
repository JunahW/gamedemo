package com.example.gamedemo.server.game.bag.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

/**
 * @author wengj
 * @description
 * @date 2019/5/29
 */
@Resource
public class ItemResource implements ResourceInterface {
    /**
     * 配置的唯一id
     */
    @ExcelColumn(columnName = "id")
    private String itemId;

    /**
     * 道具名称
     */
    @ExcelColumn(columnName = "name")
    private String name;

    /**
     * 装备的类型
     */
    @ExcelColumn(columnName = "itemType")
    private int itemType;

    /**
     * 物品最大堆叠数量
     */
    @ExcelColumn(columnName = "overLimit")
    private int overLimit;

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public int getItemType() {
        return itemType;
    }

    public int getOverLimit() {
        return overLimit;
    }

    public String getItemId() {
        return itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setOverLimit(int overLimit) {
        this.overLimit = overLimit;
    }

    @Override
    public Object getId() {
        return this.itemId;
    }
}
