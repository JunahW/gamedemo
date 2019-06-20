package com.example.gamedemo.executor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wengj
 * @description
 * @date 2019/6/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutorTest {

  @Test
  public void testExecutor() {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(
        new Runnable() {
          @Override
          public void run() {
            System.out.println("1111111111");
          }
        },
        2,
        3,
        TimeUnit.SECONDS);

    service.scheduleAtFixedRate(
        new Runnable() {
          @Override
          public void run() {
            System.out.println("0000000000");
          }
        },
        1,
        2,
        TimeUnit.SECONDS);
    while (true) {}
  }
}
