package apprun.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import apprun.daos.CacheRepository;
import apprun.daos.WalletRepository;

import apprun.models.Wallet;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.*;


@Service
public class WalletService {

	@Autowired
	private WalletRepository walletrepo;
	
	@Autowired
	private CacheRepository cacherepo;
	

	@Autowired
	private KafkaTemplate<String,String> template;
	
	private ObjectMapper mapper=new ObjectMapper();
	
	@Value("${wallet.initial.balance}")
	private Double balance;
	
	private static final String CREATED_USER_TOPIC="create_user";
	
	private static final String CREATED_TXN_TOPIC="create_txn";
	private static final String WALLET_UPDATED_TOPIC="wallet_update";
	
	
	@KafkaListener(topics= {CREATED_USER_TOPIC},groupId="walletcreators")
	public void create(String msg) throws ParseException
	{
		JSONObject obj= (JSONObject)new JSONParser().parse(msg);
		String username=(String)obj.get("username");
		
		Wallet wallet= Wallet.builder()
				.walletid(username)
				.balance(balance)
				.currency("INR")
				.build();
		walletrepo.save(wallet);
		
	
		
	}
	@KafkaListener(topics= {CREATED_TXN_TOPIC},groupId="createdtxn")
	public String update(String msg) throws ParseException, ClassNotFoundException, IOException
	{
		JSONObject receivedload=(JSONObject)new JSONParser().parse(msg);
		
		String senderid=(String) receivedload.get("sender");
		String receiverid=(String) receivedload.get("receiver");
		String txnid=(String) receivedload.get("externaltxnid");
		String tag=(String) receivedload.get("desc");
		
		Double amt=(Double)receivedload.get("amt");
		
		Wallet Senderwallet=walletrepo.getByWalletid(senderid);
		Wallet Receiverwallet=walletrepo.getByWalletid(receiverid);
		
		if(Senderwallet==null|| Senderwallet.getBalance()<amt|| Receiverwallet==null)
		{
			JSONObject obj=createload(senderid,receiverid,txnid,amt);
			obj.put("status", "FAILURE");
			obj.put("sendbalance",Senderwallet==null?0:Senderwallet.getBalance());
			obj.put("receivebalance",Receiverwallet==null?0:Receiverwallet.getBalance());
			template.send(WALLET_UPDATED_TOPIC, this.mapper.writeValueAsString(obj));
		}
		else
		{
			JSONObject obj=createload(senderid,receiverid,txnid,amt);
			Senderwallet.setBalance(Senderwallet.getBalance()-amt);
			Receiverwallet.setBalance(Receiverwallet.getBalance()+amt);
			obj.put("status", "SUCCESS");
			obj.put("sendbalance",Senderwallet.getBalance());
			obj.put("receivebalance",Receiverwallet.getBalance());
			
			walletrepo.save(Senderwallet);
			walletrepo.save(Receiverwallet);
			template.send(WALLET_UPDATED_TOPIC, this.mapper.writeValueAsString(obj));
		}
		
		return txnid;
	
		
	}
	
	public List<Wallet> getwallets() {
		// TODO Auto-generated method stub
		List<Wallet> wallets=walletrepo.findAll();
		
		return wallets;
	}

	public Wallet getWalletByUserName(String userid) {
		// TODO Auto-generated method stub
		Wallet wallet=cacherepo.getUserByUserName(userid);
		if(wallet==null)
		{
			wallet=walletrepo.getWalletByUserName(userid);
			cacherepo.create(wallet);
		}
		return wallet;
	}
	//
	private JSONObject createload(String senderid,String receiverid,String txnid,Double amt)
	{
		JSONObject obj=new JSONObject();
		
		obj.put("senderid",senderid);
		obj.put("receiverid",receiverid);
		obj.put("externaltxnid",txnid);
	
		obj.put("amt",amt);
		return obj;
	}
	

}
