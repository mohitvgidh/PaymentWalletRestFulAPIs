package App.Services;

import App.Model.Report;
import App.Model.Transaction;
import App.Model.TransactionList;
import App.Model.WalletUser;
import App.dtos.ReportRequest;
import jakarta.validation.Valid;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONObject;
import org.springframework.http.*;

@Service
public class ReportService {

	
	private RestTemplate resttemplate=new RestTemplate();
	
	public List<Transaction> gettransactions(@Valid ReportRequest query) throws ParseException {
		// TODO Auto-generated method stub
		
		

		String url = "http://localhost:6000/get/txn";
		
		

		
		TransactionList txns =this.resttemplate.postForObject(url,query,TransactionList.class);
	    return txns.getTransacts();
		
		
	}
	
	public Report generateReport(@Valid ReportRequest query) throws ParseException
	{
		List<Transaction> txns=gettransactions(query);
		WalletUser user=this.resttemplate.getForObject("http://localhost:9000/get/usernameservice/reportserv"+query.getUserid(), WalletUser.class);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); //2024-02-20 17:24:41.474
		Date fromdate = formatter.parse(query.getFromDate());
		Date todate=formatter.parse(query.getToDate());
		
		List<Transaction> expendituretxn=txns.stream().filter((txn)->txn.getSenderid().equals(user.getMobileno())).collect(Collectors.toList());
		List<Transaction> gaintxn=txns.stream().filter((txn)->txn.getReceiverid().equals(user.getMobileno())).collect(Collectors.toList());
		Double exp=0D;
		for(Transaction t:expendituretxn)
			exp+=t.getAmount();
		Double gain=0D;
		for(Transaction tx:gaintxn)
			gain+=tx.getAmount();
		Transaction firsttxn=txns.stream().sorted((txn1,txn2)->txn1.getCreatedOn().compareTo(txn2.getCreatedOn())).findFirst().get();
		Double startbalance=firsttxn.getSenderid()==user.getMobileno()?firsttxn.getSendbalance()+firsttxn.getAmount():firsttxn.getReceivebalance()-firsttxn.getAmount();
		Double endbalance=startbalance+(gain-exp);
		return Report.builder().txns(txns)
				.user(user)
				.FromDate(fromdate)
				.ToDate(todate)
				.startbalance(startbalance)
				.endbalance(endbalance)
				.TotalExpenditure(exp)
				.txncount(txns.size())
				.build();
		
	}

}
