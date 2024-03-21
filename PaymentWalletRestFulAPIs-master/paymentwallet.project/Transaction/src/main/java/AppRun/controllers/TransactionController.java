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
	
	@GetMapping("get/userdetails")	

	public List<Transaction> getTxnsByUserName(String userid)
	{
		return txnservice.getTxnsByUserName(userid);
	}
	
	@GetMapping("/get")//get all transactions for analytics
	
	public List<Transaction> listtransactions()
	{
		return txnservice.getTxns();
	}
}
