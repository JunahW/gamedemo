package com.example.gamedemo.server.game.base.gameobject;

import java.util.Objects;

/**
 * @author wengj
 * @description:对象的唯一id，根据UniqueIdUtils生成
 * @date 2019/5/29
 */
public abstract class GameObject {
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GameObject that = (GameObject) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
