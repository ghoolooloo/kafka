package net.yohol.kafka.consumer;

import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

public class ConsumerRunner implements Runnable {
  private final AtomicBoolean running = new AtomicBoolean(true);
  private Consumer<String, String> consumer;
  private final Collection<String> topics;
  private final String groupId;

  public ConsumerRunner(Collection<String> topics, String groupId) {
    this.topics = topics;
    this.groupId = groupId;
  }

	@Override
	public void run() {
		Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka.mayocase.com:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10000");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

    consumer = new KafkaConsumer<>(props);

    try {
      consumer.subscribe(topics);
      while (running.get()) {
        ConsumerRecords<String, String> records = consumer.poll(10000);
        for (ConsumerRecord<String, String> record : records)
          System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
      }
    } catch (WakeupException e) {
      System.out.println("抛出WakeupException@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
      // Ignore exception if closing
      if (running.get()) throw e;
    } finally {
      consumer.close();
      System.out.println("Consumer已关闭。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
    }
  }
  
  // Shutdown hook which can be called from a separate thread
  public void shutdown() {
    running.set(false);
    consumer.wakeup();
}
}