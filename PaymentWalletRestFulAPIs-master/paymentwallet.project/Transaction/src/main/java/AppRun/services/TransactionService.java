package AppRun.services;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import AppRun.dtos.*;
import AppRun.daos.CacheRepository;
import AppRun.daos.TxnRepository;
import AppRun.models.Transaction;
import AppRun.models.TransactionStatus;
import jakarta.validation.Valid;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class TransactionService {

	
	@Autowired
	private TxnRepository txnrepo;
	
	@Autowired
	private CacheRepository cacherepo;
	
	private RestTemplate resttemplate=new RestTemplate();
	
	
	
	@Autowired
	private KafkaTemplate<String,String> template;
	
	private ObjectMapper mapper=new ObjectMapper();
	
	private static final String CREATED_TXN_TOPIC="create_txn";
	private static final String COMPLETED_TXN_TOPIC="complete_txn";
	private static final String WALLET_UPDATED_TOPIC="wallet_update";
	
	
	public List<Transaction> getTxnsByUserName(String userid) {
		// TODO Auto-generated method stub
		List<Transaction> txns=txnrepo.getByUserName(userid);
		
		
		
		return txns;
	}

	public List<Transaction> getTxns() {
		// TODO Auto-generated method stub
		List<Transaction> txns=txnrepo.findAll();
		
		return txns;
	}
    
	public void create(Transaction txn) throws JsonProcessingException {
		// TODO Auto-generated method stub
		
    	txn.setTransactionstatus(TransactionStatus.valueOf("PENDING"));
    	txnrepo.save(txn);
    	
		JSONObject sentload=new JSONObject();
		sentload.put("sender", txn.getSenderid());
		sentload.put("receiver", txn.getReceiverid());
		sentload.put("amt", txn.getAmount());
		sentload.put("desc", txn.getReason());
		sentload.put("externaltxnid", txn.getTxnid());
		
		template.send(CREATED_TXN_TOPIC,this.mapper.writeValueAsString(sentload));
		
		
		
		
		
		
	}
	@KafkaListener(topics= {WALLET_UPDATED_TOPIC},groupId="walletupdate")
    public void updatetxn(String msg) throws ParseException, JsonProcessingException
    {
		JSONObject receivedload=(JSONObject)new JSONParser().parse(msg);
		String status=(String) receivedload.get("status");
		String senderid=(String) receivedload.get("senderid");
		String receiverid=(String) receivedload.get("receiverid");
		String txnid=(String) receivedload.get("externaltxnid");
		Double sendbalance=(Double)receivedload.get("sendbalance");
		Double receivebalance=(Double)receivedload.get("receivebalance");
		Double amt=(Double)receivedload.get("amt");
	
		
		
	    Transaction txn=txnrepo.getByTxnid(txnid);
	    txn.setTransactionstatus(TransactionStatus.valueOf(status));
	    txn.setSendbalance(sendbalance);
	    txn.setReceivebalance(receivebalance);
	    txnrepo.save(txn);
	   
	    JSONObject sender=this.resttemplate.getForObject("http://localhost:9000/get/usernameservice/txnservice"+senderid, JSONObject.class);
	    JSONObject receiver=this.resttemplate.getForObject("http://localhost:9000/get/usernameservice/txnservice"+receiverid, JSONObject.class);
	    
	    String senderemail=sender==null?null:(String)sender.get("email");
	    String receiveremail=receiver==null?null:(String)receiver.get("email");
	    JSONObject sentload=new JSONObject();
		sentload.put("sender", senderemail);
		sentload.put("receiver", receiveremail);
		sentload.put("amt",amt);
		sentload.put("sendbal", sendbalance);
		sentload.put("recbal", receivebalance);
		sentload.put("status", status);
		sentload.put("externaltxnid", txnid);
		
		
		template.send(COMPLETED_TXN_TOPIC, this.mapper.writeValueAsString(sentload));
	    
		
    }

	public List<Transaction> getTxnsByUserNameAndDateAndReason(String userid, @Valid ReportQueryRequestDate request) throws java.text.ParseException {
		// TODO Auto-generated method stub
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); //2024-02-20 17:24:41.474
		Date fromdate = formatter.parse(request.getFromDate());
		Date todate=formatter.parse(request.getToDate());
		
		String reason=request.getReason();
		if(reason==null)
			return txnrepo.getByDate(fromdate,todate,userid);
		else
			return txnrepo.getByDateAndReason(fromdate,todate,reason,userid);
			
		
	}

}
