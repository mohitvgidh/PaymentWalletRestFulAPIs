package apprun.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import apprun.models.Wallet;

public interface WalletRepository extends JpaRepository<Wallet,Integer> {
	
	@Query("select u from Wallet u where u.walletid=?1")
	Wallet getWalletByUserName(String UserName);

	Wallet getByWalletid(String senderid);

}
