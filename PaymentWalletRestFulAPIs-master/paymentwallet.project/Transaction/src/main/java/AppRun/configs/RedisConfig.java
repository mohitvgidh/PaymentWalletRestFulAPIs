package AppRun.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import AppRun.models.Transaction;


@Configuration
public class RedisConfig {
	
	@Bean
	public LettuceConnectionFactory getConnection()
	{
		RedisStandaloneConfiguration config=new RedisStandaloneConfiguration();
		
		LettuceConnectionFactory conn=new LettuceConnectionFactory(config);
		return conn;
	}
	@Bean
	public RedisTemplate<String,List<Transaction>> gettemplate()
	{
		RedisTemplate<String,List<Transaction>> template=new RedisTemplate<>();
		template.setConnectionFactory(getConnection());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		return template;
	}

}
