package AppRun.models;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//Sender wallet unique id
	@Column(nullable=false)
	private String senderid;

	//Receiver wallet unique id
	@Column(nullable=false)
	private String receiverid;
	
	//external transaction id
	@Column(nullable=false,unique=true)
	private String txnid;
	
	@Min(1)
	private Double amount; //amount in smallest denomination
	
	private String reason;
	
	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionstatus;
	
	@CreationTimestamp
	private Date createdOn;
	
	@UpdateTimestamp
	private Date updatedOn;
	
	@Column
	private Double sendbalance;
	
	@Column
	private Double receivebalance;
	

}
