package com.example.gamedemo.server.game.bag.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;
import org.springframework.util.StringUtils;

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
    private int itemId;

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

    /**
     * 玩家类型（职业）用字符串表示数据","隔开
     */
    @ExcelColumn(columnName = "playerType")
    private String playerType;

    private int[] playerTypes;

    /**
     * 装备的位置
     */
    @ExcelColumn(columnName = "position")
    private int position;

    public void setItemId(int itemId) {
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

    public int getItemId() {
        return itemId;
    }

    public String getPlayerType() {
        return playerType;
    }

    public int[] getPlayerTypes() {
        return playerTypes;
    }

    public void setPlayerTypes(int[] playerTypes) {
        this.playerTypes = playerTypes;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public void setOverLimit(int overLimit) {
        this.overLimit = overLimit;
    }

    @Override
    public Object getId() {
        return this.itemId;
    }

    @Override
    public void postInit() {
        if (playerTypes == null) {
            if (StringUtils.isEmpty(this.getPlayerType())) {
                return;
            }
            String[] playerStrings = this.getPlayerType().split(",");
            int[] array = new int[playerStrings.length];
            for (int i = 0; i < playerStrings.length; i++) {
                array[i] = Integer.parseInt(playerStrings[i]);
            }
            this.playerTypes = array;
        }
    }

}
