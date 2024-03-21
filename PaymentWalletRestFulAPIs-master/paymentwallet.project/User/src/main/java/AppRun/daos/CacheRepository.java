package AppRun.daos;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import AppRun.models.WalletUser;

@Repository
public class CacheRepository {

	
	@Autowired
	private RedisTemplate<String,WalletUser> template;
	
	private static final String USER_KEY="user::";
	
	
	public WalletUser getUserByUserName(String username)
	{
		Object obj= template.opsForValue().get(getkey(username));
		
		return obj==null?null:(WalletUser)obj;
		
	}
	public void create(WalletUser user)
	{
		template.opsForValue().set(getkey(user.getMobileno()), user, 600, TimeUnit.SECONDS);
	}


	private String getkey(String userid) {
		// TODO Auto-generated method stub
		return USER_KEY+userid;
	}
}
