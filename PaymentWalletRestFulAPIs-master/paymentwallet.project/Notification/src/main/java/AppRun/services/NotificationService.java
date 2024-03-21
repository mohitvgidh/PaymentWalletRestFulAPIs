package AppRun.services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	@Autowired
	SimpleMailMessage simplemail;
	
	@Autowired
	JavaMailSender mailsender;
	
	private static final String COMPLETED_TXN_TOPIC="complete_txn";
	
	@KafkaListener(topics= {COMPLETED_TXN_TOPIC},groupId="txncomplete")
	public void notify(String msg) throws ParseException
	{
		JSONObject receivedload=(JSONObject)new JSONParser().parse(msg);
		String status=(String) receivedload.get("status");
		String senderemail=(String) receivedload.get("sender");
		String receiveremail=(String) receivedload.get("receiver");
		String txnid=(String) receivedload.get("externaltxnid");
		Double sendbalance=(Double)receivedload.get("sendbal");
		Double receivebalance=(Double)receivedload.get("recbal");
		Double amt=(Double)receivedload.get("amt");
		String bucketmsg=(String) receivedload.get("bucketmsg");
		
		String sendmsg=getSendermsg(status,sendbalance,amt,txnid,bucketmsg);
		String receivemsg=getReceivermsg(status,receivebalance,amt,txnid);
		
		if(sendmsg!=null && sendmsg.length()>0)
		{
			simplemail.setTo(senderemail);
			simplemail.setFrom("paymentwallet83@gmail.com");
			simplemail.setSubject("Payment Wallet Tranaction Updates");
			simplemail.setText(sendmsg);
			mailsender.send(simplemail);
			
		}
		if(receivemsg!=null && receivemsg.length()>0)
		{
			simplemail.setTo(receiveremail);
			simplemail.setFrom("paymentwallet83@gmail.com");
			simplemail.setSubject("Payment Wallet Tranaction Updates");
			simplemail.setText(receivemsg);
			mailsender.send(simplemail);
		}
		
		
		
	}
	private String getSendermsg(String status,Double sendbalance,Double amt,String txnid,String bucketmsg)
	{
		String msg="";
		if(status.equals("SUCCESS"))
		{
			msg=txnid+" : Your account is debited with amount "+amt.toString()+" ,available balance is "+sendbalance.toString()+". "+"\n"+bucketmsg;
		}
		else
		{
			msg=txnid+" : Your latest transaction has failed.";
			
		}
		return msg;
		
	}
	private String getReceivermsg(String status,Double receivebalance,Double amt,String txnid)
	{
		String msg="";
		if(status.equals("SUCCESS"))
		{
			msg=txnid+" : Your account is credited with amount "+amt.toString()+" ,available balance is "+receivebalance.toString()+".";
		}
		
		return msg;
		
	}
	/*
	 * sentload.put("sender", senderemail);
		sentload.put("receiver", receiveremail);
		sentload.put("amt",amt);
		sentload.put("sendbal", sendbalance);
		sentload.put("recbal", receivebalance);
		sentload.put("status", status);
		sentload.put("externaltxnid", txnid);
	 */

}
