package App.Model;
import java.util.*;
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
