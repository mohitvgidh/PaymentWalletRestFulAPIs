package AppRun.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import AppRun.dtos.AuthenticateUserRequestData;
import AppRun.dtos.CreateUserRequestData;
import AppRun.models.WalletUser;
import AppRun.services.UserService;
import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	UserService userservice;
	
	
	
	@PostMapping("/create")
	public void createUser(@RequestBody @Valid CreateUserRequestData data) throws JsonProcessingException
	{
		userservice.create(data.to());
	}
	
	@PostMapping("/authenticate")
	public String jwtauth(@RequestBody @Valid AuthenticateUserRequestData data) throws Exception
	{
		return userservice.getjwt(data);
	}
	@GetMapping("/get/usernameservice/{secretusername}")
	public WalletUser getUserByUserNameForService(@PathVariable String secretusername )
	{
		if(userservice.checkserviceauth(secretusername))
		{
			return userservice.getUserByUserName(secretusername.substring(10));
		}
		return null;
	}
	
	
	@GetMapping("/get/userdetails")
	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public WalletUser getUserByUserName(Authentication authentication)
	{
		return userservice.getUserDetails(authentication);
	}
	
	@GetMapping("/get/username/{username}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public WalletUser getUserByUserName(@PathVariable String username)
	{
		return userservice.getUserByUserName(username);
	}
	
	@GetMapping("/get/welcome")
	public String welcomeuser()
	{
		return "Welcome Mohit";
	}
	
	@GetMapping("/get")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<WalletUser> listusers()
	{
		return userservice.getUsers();
	}

}
