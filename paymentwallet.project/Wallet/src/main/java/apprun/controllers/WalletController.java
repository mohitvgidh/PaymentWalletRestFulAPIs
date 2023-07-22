package apprun.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import apprun.models.Wallet;
import apprun.services.WalletService;



@RestController
public class WalletController {

	@Autowired
	WalletService walletservice;

	@GetMapping("/get")
	public List<Wallet> listwallets()
	{
		return walletservice.getwallets();
	}
	
	@GetMapping("/get/username/{usrname}")
	public Wallet getWalletByUserName(@PathVariable String usrname)
	{
		return walletservice.getWalletByUserName(usrname);
	}
}
