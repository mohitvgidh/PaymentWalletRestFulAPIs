package apprun.daos;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import apprun.models.Wallet;



@Repository
public class CacheRepository {

	@Autowired
	private RedisTemplate<String,Wallet> template;
	
	private static final String WALLET_KEY="wallet::";
	
	
	public Wallet getUserByUserName(String username)
	{
		Object obj= template.opsForValue().get(getkey(username));
		
		return obj==null?null:(Wallet)obj;
		
	}
	public void create(Wallet wallet)
	{
		template.opsForValue().set(getkey(wallet.getWalletid()), wallet, 600, TimeUnit.SECONDS);
	}


	private String getkey(String userid) {
		// TODO Auto-generated method stub
		return WALLET_KEY+userid;
	}

}
