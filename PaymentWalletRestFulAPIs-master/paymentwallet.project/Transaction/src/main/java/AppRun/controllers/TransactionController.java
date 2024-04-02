package AppRun.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import AppRun.dtos.CreateTxnRequestData;
import AppRun.dtos.ReportQueryRequestDate;
import AppRun.models.*;
import AppRun.dtos.*;
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
	
	@GetMapping("get/userdetails/{userid}")	

	public List<Transaction> getTxnsByUserName(@PathVariable String userid)
	{
		return txnservice.getTxnsByUserName(userid);
	}
	
	@PostMapping("get/txn")	
	public TransactionList getTxnsByUserNameAndDate(@RequestBody @Valid ReportQueryRequestDate request) throws ParseException
	{
		TransactionList txnlist=new TransactionList(); 
		txnlist.setTransacts(txnservice.getTxnsByUserNameAndDateAndReason(request.getUserid(),request));
		return txnlist;
	}
	
	@GetMapping("/get")//get all transactions for analytics
	
	public List<Transaction> listtransactions()
	{
		return txnservice.getTxns();
	}
}
