package AppRun.dtos;



import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import AppRun.models.Transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTxnRequestData {

	
	
	@NotNull
	@JsonProperty("sender")
	private String senderid;

	//Receiver wallet unique id
	@NotNull
	@JsonProperty("receiver")
	private String receiverid;
	
	@Min(1)
	@JsonProperty("amt")
	private Double amount; //amount in smallest denomination
	
	@JsonProperty("reason")
	private String reason;
	
	public Transaction to() {
		// TODO Auto-generated method stub
		return Transaction.builder()
				.receiverid(receiverid)
				.senderid(senderid)
				.amount(amount)
				.reason(reason)
				.txnid(UUID.randomUUID().toString())
				.build();
	}

}
