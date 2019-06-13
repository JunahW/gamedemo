package com.example.gamedemo.common.event;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author wengj
 * @description
 * @date 2019/6/11
 */
public class ReceiverInvoke {
  /** bean对象 */
  private Object bean;

  /** 方法对象 */
  private Method method;

  public ReceiverInvoke(Object bean, Method method) {
    this.bean = bean;
    this.method = method;
  }

  /**
   * 执行方法
   *
   * @param event
   */
  public void invoke(Event event) {
    ReflectionUtils.makeAccessible(method);
    ReflectionUtils.invokeMethod(method, bean, event);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReceiverInvoke that = (ReceiverInvoke) o;
    return Objects.equals(bean, that.bean) && Objects.equals(method, that.method);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bean, method);
  }
}
