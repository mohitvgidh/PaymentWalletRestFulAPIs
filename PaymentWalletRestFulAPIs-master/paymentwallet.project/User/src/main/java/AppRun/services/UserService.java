package AppRun.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import AppRun.daos.CacheRepository;
import AppRun.daos.UserRepository;
import AppRun.dtos.AuthenticateUserRequestData;
import AppRun.dtos.CreateUserRequestData;
import AppRun.models.WalletUser;

import jakarta.validation.Valid;



@Service
public class UserService {
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	PasswordEncoder encoder;

	
	@Autowired
	private CacheRepository cacherepo;
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired
	private UserDetailsDBService userdetailsservice;
	
	@Autowired
	private JwtConfig jwtcfg;
	@Autowired
	private KafkaTemplate<String,String> template;
	
	private ObjectMapper mapper=new ObjectMapper();
	private HashMap<String,Integer> mp=new HashMap<>();
	
	
	private static final String CREATED_USER="create_user";
	
	
	
	
	
	public void create(WalletUser user) throws JsonProcessingException
	{
		user.setPassword(encoder.encode(user.getPassword()));
		userrepo.save(user);
		
		JSONObject obj=new JSONObject();
		obj.put("username", user.getMobileno());
		obj.put("email", user.getEmail());
		
		template.send(CREATED_USER,this.mapper.writeValueAsString(obj));
		
	
		
	}
	
	public WalletUser getUserDetails(Authentication authentication)//give mobilenumber
	{
		WalletUser user=cacherepo.getUserByUserName(authentication.getName());
		if(user==null)
		{
			user=userrepo.getUserByUserName(authentication.getName());
			cacherepo.create(user);
		}
		return user;
	}
	
	public List<WalletUser> getUsers()
	{
		List<WalletUser> users=userrepo.findAll();
		
		return users;
	}

	public WalletUser getUserByUserName(String username) {
		// TODO Auto-generated method stub
		return userrepo.getUserByUserName(username);
	}

	public String getjwt(@Valid AuthenticateUserRequestData data) throws Exception {
		// TODO Auto-generated method stub
		try
		{
		authmanager.authenticate(new UsernamePasswordAuthenticationToken(data.getMobileno(),data.getPassword()));
		}
		catch(BadCredentialsException e)
		{
			throw new Exception("Incorrect username or password",e);
		}
		UserDetails user=userdetailsservice.loadUserByUsername(data.getMobileno());
		return jwtcfg.generateToken(user);
		
	}

	public boolean checkserviceauth(String username) {
		// TODO Auto-generated method stub
		String secret=username.substring(0,10);
		mp.put("txnservice", 1);
		mp.put("walletserv", 1);
		if(mp.containsKey(secret))
			return true;
		return false;
	}

	 

}
