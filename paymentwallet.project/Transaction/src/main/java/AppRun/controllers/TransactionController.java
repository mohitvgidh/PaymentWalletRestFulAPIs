package AppRun.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import AppRun.dtos.CreateTxnRequestData;
import AppRun.models.Transaction;
import AppRun.services.TransactionService;
import jakarta.validation.Valid;

@RestController
public class TransactionController {


	@Autowired
	TransactionService txnservice;
	
	@PostMapping("/create")
	public void createTxn(@RequestBody @Valid CreateTxnRequestData data) throws JsonProcessingException
	{
		txnservice.create(data.to());
	}
	
	@GetMapping("/get/username/{usrname}")//get transactions based on user id
	public List<Transaction> getTxnsByUserName(@PathVariable String usrname)
	{
		return txnservice.getTxnsByUserName(usrname);
	}
	
	@GetMapping("/get")//get all transactions for analytics
	public List<Transaction> listusers()
	{
		return txnservice.getTxns();
	}
}
