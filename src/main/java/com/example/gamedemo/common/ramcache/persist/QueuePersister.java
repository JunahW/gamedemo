package com.example.gamedemo.common.ramcache.persist;

import java.util.concurrent.BlockingDeque;

/**
 * @author wengj
 * @description：队列持久化处理器
 * @date 2019/5/24
 */
public class QueuePersister implements Persister {

  /** 更新队列 */
  private BlockingDeque<Element> queue;

  /** 消费线程 */
  private QueueConsumer consumer;
}
