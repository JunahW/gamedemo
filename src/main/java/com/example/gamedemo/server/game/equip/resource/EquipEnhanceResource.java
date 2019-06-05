package com.example.gamedemo.server.game.equip.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.resource.ResourceInterface;

/**
 * @author wengj
 * @description：装备升阶消耗资源
 * @date 2019/6/5
 */
public class EquipEnhanceResource implements ResourceInterface {

    /**
     * 唯一id
     */
    @ExcelColumn(columnName = "id")
    private String id;

    /**
     * 消耗的道具json格式
     */
    @ExcelColumn(columnName = "consume")
    private String consume;

    /**
     * 增强的属性
     */
    @ExcelColumn(columnName = "enhanceAttr")
    private String enhanceAttr;


    @Override
    public Object getId() {
        return null;
    }

    @Override
    public void postInit() {

    }
}
