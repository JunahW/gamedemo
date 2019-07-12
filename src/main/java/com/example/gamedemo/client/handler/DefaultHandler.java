package com.example.gamedemo.client.handler;

import com.example.gamedemo.client.utils.JsonFormatterUtils;

/**
 * @author wengj
 * @description
 * @date 2019/7/12
 */
public class DefaultHandler {

  public void defaultMethod(Object message) {
    JsonFormatterUtils.printObjectJsonString(message);
  }
}
