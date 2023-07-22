package AppRun.daos;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import AppRun.models.WalletUser;

public interface UserRepository extends JpaRepository<WalletUser,Integer> {

	@Query("select u from WalletUser u where u.mobileno=?1")
	WalletUser getUserByUserName(String UserName);

	

}
