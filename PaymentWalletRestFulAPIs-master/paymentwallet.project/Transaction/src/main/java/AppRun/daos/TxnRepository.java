package AppRun.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import AppRun.models.Transaction;

@Repository
public interface TxnRepository extends JpaRepository<Transaction,Integer>{

	
	@Query("select t from Transaction t where t.senderid=?1 or t.receiverid=?1")
	List<Transaction> getByUserName(String usrname);

	Transaction getByTxnid(String txnid);

	
}
