package apprun.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import apprun.models.Bucket;

@Repository
public interface BucketRepository extends JpaRepository<Bucket,Integer>{

	@Query("select b from Bucket b where b.walletid=?1")
	List<Bucket> findByWalletId(String userid);

	@Query("select b from Bucket b where b.tag=?1 and b.walletid=?2")
	Bucket getByTagAndWalletId(String tag, String walletid);

}
