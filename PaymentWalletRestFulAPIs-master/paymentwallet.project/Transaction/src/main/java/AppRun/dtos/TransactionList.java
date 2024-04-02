package AppRun.dtos;


import java.util.*;

import AppRun.models.Transaction;
import lombok.*;

@Getter
@Setter
public class TransactionList {
	
	private List<Transaction> transacts;
	
	public TransactionList()
	{
		transacts=new ArrayList<>();
	}

}
