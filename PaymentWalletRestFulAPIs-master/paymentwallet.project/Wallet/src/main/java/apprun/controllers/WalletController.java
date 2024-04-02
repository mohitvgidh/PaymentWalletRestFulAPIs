package apprun.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import apprun.models.Wallet;
import apprun.services.WalletService;
import jakarta.validation.Valid;



@RestController
public class WalletController {

	@Autowired
	WalletService walletservice;

	@GetMapping("/get")
	
	public List<Wallet> listwallets()
	{
		return walletservice.getwallets();
	}
	
	
	
	@GetMapping("/get/userdetails")
	
	public Wallet getWalletByUserName(String userid)
	{
		return walletservice.getWalletByUserName(userid);
	}
	
	
}
