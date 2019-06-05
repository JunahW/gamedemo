package com.example.gamedemo.server.game.player.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.server.game.attribute.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wengj
 * @description:玩家属性
 * @date 2019/6/3
 */
@Resource
public class BaseAttributeResource implements ResourceInterface {

    /**
     * 玩家类型（职业）
     */
    @ExcelColumn(columnName = "playerType")
    private String playerType;

    /**
     * 生命值
     */
    @ExcelColumn(columnName = "hp")
    private long hp;

    /**
     * 魔法值
     */
    @ExcelColumn(columnName = "mp")
    private long mp;

    /**
     * 攻击力
     */
    @ExcelColumn(columnName = "attack")
    private long attack;

    /**
     * 防御力
     */
    @ExcelColumn(columnName = "defense")
    private long defense;

    /**
     * 攻击下限
     */
    @ExcelColumn(columnName = "attackLower")
    private long attackLower;

    /**
     * 攻击上限
     */
    @ExcelColumn(columnName = "attackUpper")
    private long attackUpper;

    /**
     * 防御上限
     */
    @ExcelColumn(columnName = "defenseLower")
    private long defenseLower;

    /**
     * 防御上限
     */
    @ExcelColumn(columnName = "defenseUpper")
    private long defenseUpper;

    private List<Attribute> attributes;

    public String getPlayerType() {
        return playerType;
    }

    public long getHp() {
        return hp;
    }

    public long getMp() {
        return mp;
    }

    public long getAttack() {
        return attack;
    }

    public long getDefense() {
        return defense;
    }

    public long getAttackUpper() {
        return attackUpper;
    }

    public long getDefenseUpper() {
        return defenseUpper;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public void setMp(long mp) {
        this.mp = mp;
    }

    public void setAttack(long attack) {
        this.attack = attack;
    }

    public void setDefense(long defense) {
        this.defense = defense;
    }

    public void setAttackUpper(long attackUpper) {
        this.attackUpper = attackUpper;
    }

    public long getAttackLower() {
        return attackLower;
    }

    public long getDefenseLower() {
        return defenseLower;
    }

    public void setDefenseUpper(long defenseUpper) {
        this.defenseUpper = defenseUpper;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setAttackLower(long attackLower) {
        this.attackLower = attackLower;
    }

    public void setDefenseLower(long defenseLower) {
        this.defenseLower = defenseLower;
    }

    @Override
    public Object getId() {
        return this.playerType;
    }

    /**
     * 获取基础属性列表
     *
     * @return
     */
    public List<Attribute> getPlayerBaseAttribute() {
        return this.attributes;
    }

    @Override
    public void postInit() {
        if (this.attributes == null) {
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("HP", this.hp));
            attributes.add(new Attribute("MP", this.mp));
            attributes.add(new Attribute("ATTACK", this.attack));
            attributes.add(new Attribute("DEFENSE", this.defense));
            attributes.add(new Attribute("ATTACK_LOWER", this.attackLower));
            attributes.add(new Attribute("ATTACK_UPPER", this.attackUpper));
            attributes.add(new Attribute("DEFENSE_LOWER", this.defenseLower));
            attributes.add(new Attribute("DEFENSE_UPPER", this.defenseUpper));
            this.attributes = attributes;
        }
    }
}
