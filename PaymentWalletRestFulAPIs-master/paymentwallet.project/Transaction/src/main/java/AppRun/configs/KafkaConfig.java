package AppRun.configs;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

@Configuration
public class KafkaConfig {
	
	@Bean
	ProducerFactory<String,String> getproducer()
	{
		Properties properties=new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
		return new DefaultKafkaProducerFactory(properties);
		
	}
	@Bean
	ConsumerFactory<String,String> getconsumer()
	{
		Properties properties=new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
		return new DefaultKafkaConsumerFactory(properties);
		
	}
	
	@Bean
	KafkaTemplate<String,String> getKafkatemplate()
	{
		return new KafkaTemplate<>(getproducer());
	}

}
