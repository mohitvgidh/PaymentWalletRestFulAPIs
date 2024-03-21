package AppRun.configs;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.apache.kafka.common.serialization.StringSerializer;



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
	KafkaTemplate<String,String> getKafkatemplate()
	{
		return new KafkaTemplate<>(getproducer());
	}

}
