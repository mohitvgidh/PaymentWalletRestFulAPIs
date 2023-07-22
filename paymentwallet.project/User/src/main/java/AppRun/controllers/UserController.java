package AppRun.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

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
	
	@GetMapping("/get/username/{usrname}")
	public WalletUser getUserByUserName(@PathVariable String usrname)
	{
		return userservice.getUserByUserName(usrname);
	}
	
	@GetMapping("/get")
	public List<WalletUser> listusers()
	{
		return userservice.getUsers();
	}

}
