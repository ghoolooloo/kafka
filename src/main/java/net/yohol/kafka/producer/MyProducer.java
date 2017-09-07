package net.yohol.kafka.producer;

import java.util.Properties;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.context.ApplicationScoped;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

/**
 * @author Jo
 *
 */
@ApplicationScoped
public class MyProducer {	
	@Produces
  @ApplicationScoped
  @MyProducerQualifier
  public Producer<String, String> getProducer() {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka.mayocase.com:9092");
    props.put(ProducerConfig.ACKS_CONFIG, "all");
    props.put(ProducerConfig.RETRIES_CONFIG, 0);
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
    props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

    return new KafkaProducer<>(props);
  }

  public void closeMyProducer(@Disposes @MyProducerQualifier Producer<String, String> producer) {
		producer.close();
		System.out.println("MyProducer已经关闭！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
	}
}