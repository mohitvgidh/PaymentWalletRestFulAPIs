package AppRun.daos;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import AppRun.models.Transaction;

@Repository
public interface TxnRepository extends JpaRepository<Transaction,Integer>{

	
	@Query("select t from Transaction t where t.senderid=?1 or t.receiverid=?1")
	List<Transaction> getByUserName(String usrname);

	Transaction getByTxnid(String txnid);

	@Query("select t from Transaction t where t.createdOn>=?1 and t.createdOn<=?2 and (t.senderid=?3 or t.receiverid=?3)")
	List<Transaction> getByDate(Date fromdate,Date todate,String username);

	@Query("select t from Transaction t where t.createdOn>=?1 and t.createdOn<=?2 and t.reason=?3  and (t.senderid=?4 or t.receiverid=?4)")
	List<Transaction> getByDateAndReason(Date fromdate,Date todate,String reason,String username);

	
}
