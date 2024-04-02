package App.Model;

import java.util.Date;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {
	
	private List<Transaction> txns;
	
	private int txncount;
	
	private WalletUser user;
	
	private Date FromDate;
	
	private Date ToDate;
	
	private Double TotalExpenditure;
	
	private Double startbalance;
	
	private Double endbalance;
	
	

}
