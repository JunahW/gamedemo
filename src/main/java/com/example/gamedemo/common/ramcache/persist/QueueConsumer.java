package com.example.gamedemo.common.ramcache.persist;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingDeque;

/**
 * @author wengj
 * @description
 * @date 2019/5/24
 */
public class QueueConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(QueueConsumer.class);

    /**
     * 更新队列命
     */
    private final BlockingDeque<Element> queue;

    /**
     * 持久化存储器
     */
    private final Accessor accessor;

    /**
     * 当前线程自身
     */
    private final Thread thread;

    public QueueConsumer(BlockingDeque<Element> queue, Accessor accessor) {
        this.queue = queue;
        this.accessor = accessor;
        this.thread = new Thread(this, "持久化线程");
        this.thread.setDaemon(true);
        this.thread.start();
    }

    @Override
    public void run() {
        while (true) {
            Element element = null;
            Class clazz = null;
            try {
                element = queue.take();
                clazz = element.getEntityClass();
                switch (element.getType()) {
                    case FIND:
                        accessor.load(clazz, element.getEntity().getId());
                        break;
                    case SAVE:
                        accessor.save(clazz, element.getEntity());
                        break;

                    case REMOVE:
                        accessor.remove(clazz, element.getEntity().getId());
                        break;
                    case UPDATE:
                        System.out.println(accessor);
                        accessor.saveOrUpdate(clazz, element.getEntity());
                        break;
                    default:
                        logger.error("更新队列不支持元素[{}]");
                        break;
                }

            } catch (InterruptedException e) {
                logger.error("获取队列元素[{}]被非法中断:");
            } catch (RuntimeException e) {
                System.out.println(e);
                logger.error("持久化出现异常：{}", e.getStackTrace());
            }
        }
    }

    /**
     * 添加元素
     *
     * @param element
     */
    public void put(Element element) {
        try {
            queue.put(element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
