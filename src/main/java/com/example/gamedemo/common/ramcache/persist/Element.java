package com.example.gamedemo.common.ramcache.persist;

import com.example.gamedemo.common.ramcache.Entity;

/**
 * @author wengj
 * @description：队列元素
 * @date 2019/5/24
 */
public class Element {

  private final Class<? extends Entity> entityClass;
  private EventType type;
  private Entity entity;

  public Element(EventType type, Entity entity, Class<? extends Entity> entityClass) {
    this.type = type;
    this.entity = entity;
    this.entityClass = entityClass;
  }

  /**
   * 构造插入的元素
   *
   * @param entity
   * @return
   */
  public static Element saveof(Entity entity) {
    return new Element(EventType.SAVE, entity, entity.getClass());
  }

  /**
   * 构造更新的元素
   *
   * @param entity
   * @return
   */
  public static Element updateof(Entity entity) {
    return new Element(EventType.UPDATE, entity, entity.getClass());
  }

  /**
   * 构造删除的元素
   *
   * @param entity
   * @return
   */
  public static Element removeof(Entity entity) {
    return new Element(EventType.REMOVE, entity, entity.getClass());
  }

  public EventType getType() {
    return type;
  }

  public void setType(EventType type) {
    this.type = type;
  }

  public Entity getEntity() {
    return entity;
  }

  public void setEntity(Entity entity) {
    this.entity = entity;
  }

  public Class<? extends Entity> getEntityClass() {
    return entityClass;
  }
}
