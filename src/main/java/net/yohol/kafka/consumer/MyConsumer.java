package net.yohol.kafka.consumer;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class MyConsumer {
  private ExecutorService executor;
  private ConsumerRunner runner;

  @PostConstruct
  public void subscribe() {
    executor = Executors.newSingleThreadExecutor();
    runner = new ConsumerRunner(Arrays.asList("ttt"), "test");
    executor.submit(runner);
    System.out.println("MyConsumer开始订阅...........................................................................");
  }

  @PreDestroy
  public void close() {
    runner.shutdown();
    executor.shutdown();
    System.out.println("MyConsumer已关闭===========================================================================");    
  }
}