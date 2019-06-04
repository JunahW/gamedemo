package com.example.gamedemo.server.game.attribute;

/**
 * @author wengj
 * @description：属性
 * @date 2019/6/3
 */
public class Attribute {

    /**
     * 属性类型
     */
    private String type;

    /**
     * 属性值 TODO 用long
     */
    private double value;

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public Attribute() {
    }

    public Attribute(String type, double value) {
        this.type = type;
        this.value = value;
    }


}
