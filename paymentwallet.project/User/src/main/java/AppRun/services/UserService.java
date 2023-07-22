package AppRun.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import AppRun.daos.CacheRepository;
import AppRun.daos.UserRepository;
import AppRun.models.WalletUser;



@Service
public class UserService {
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private CacheRepository cacherepo;
	
	@Autowired
	private KafkaTemplate<String,String> template;
	
	private ObjectMapper mapper=new ObjectMapper();
	
	private static final String CREATED_USER="create_user";
	
	public void create(WalletUser user) throws JsonProcessingException
	{
		userrepo.save(user);
		
		JSONObject obj=new JSONObject();
		obj.put("username", user.getMobileno());
		obj.put("email", user.getEmail());
		
		template.send(CREATED_USER,this.mapper.writeValueAsString(obj));
		
	
		
	}
	
	public WalletUser getUserByUserName(String username)//give mobilenumber
	{
		WalletUser user=cacherepo.getUserByUserName(username);
		if(user==null)
		{
			user=userrepo.getUserByUserName(username);
			cacherepo.create(user);
		}
		return user;
	}
	
	public List<WalletUser> getUsers()
	{
		List<WalletUser> users=userrepo.findAll();
		
		return users;
	}

}
