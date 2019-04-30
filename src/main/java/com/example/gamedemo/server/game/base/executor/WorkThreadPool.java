package com.example.gamedemo.server.game.base.executor;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author: wengj
 * @date: 2019/4/30
 * @description:
 */
@Component
public final class WorkThreadPool {

    public static ExecutorService threadPool = new ThreadPoolExecutor(4, 8,
            1000, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));


}
