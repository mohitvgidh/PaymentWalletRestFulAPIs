package AppRun.configs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import AppRun.models.WalletUser;
@Configuration
public class RedisConfig {

	@Bean
	public LettuceConnectionFactory getconnection()
	{
		RedisStandaloneConfiguration config=new RedisStandaloneConfiguration();
		LettuceConnectionFactory conn=new LettuceConnectionFactory(config);
		return conn;
	}
	@Bean
	public RedisTemplate<String,WalletUser> getRedistemplate()
	{
		RedisTemplate<String,WalletUser> template=new RedisTemplate<String,WalletUser>();
		template.setConnectionFactory(getconnection());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		return template;
	}
}
