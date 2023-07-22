package AppRun.daos;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import AppRun.models.Transaction;

@Repository
public class CacheRepository {
	
	@Autowired
	RedisTemplate<String,List<Transaction>> template;

	private static final String TXN_PREFIX="txn::";
	public List<Transaction> getTxnByUserName(String usrname) {
		// TODO Auto-generated method stub
		List<Transaction> txns=template.opsForValue().get(getKey(usrname));
		return txns;
		
	}

	public void create(String usrname,List<Transaction> txns) {
		// TODO Auto-generated method stub
		template.opsForValue().set(getKey(usrname), txns, 600, TimeUnit.SECONDS);
		
		
	}
	public void addtokey(String usrname,Transaction txn)
	{
		List<Transaction> txns=template.opsForValue().get(getKey(usrname));
		if(txns==null)
		{
			txns=new ArrayList<Transaction>();
		}
		txns.add(txn);
		template.opsForValue().set(getKey(usrname), txns, 600, TimeUnit.SECONDS);
	}
	private String getKey(String key)
	{
		return TXN_PREFIX+key;
	}

}
