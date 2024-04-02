package App.Model;


import java.util.Date;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Transaction{
	
	
	private Integer id;
	
	//Sender wallet unique id
	
	private String senderid;

	//Receiver wallet unique id
	
	private String receiverid;
	
	//external transaction id
	
	private String txnid;
	
	
	private Double amount; //amount in smallest denomination
	
	private String reason;
	
	
	private TransactionStatus transactionstatus;
	
	
	private Date createdOn;
	
	
	private Date updatedOn;
	
	private Double sendbalance;
	
	
	private Double receivebalance;
	

}