package com.example.gamedemo.common.constant;

import com.example.gamedemo.common.session.TSession;
import io.netty.util.AttributeKey;

/**
 * @author: wengj
 * @date: 2019/5/6
 * @description: 会话id
 */
public class SessionAttributeKey {

  /** AttributeKey Session */
  public static final AttributeKey<TSession> SESSION = AttributeKey.newInstance("SESSION");
}
