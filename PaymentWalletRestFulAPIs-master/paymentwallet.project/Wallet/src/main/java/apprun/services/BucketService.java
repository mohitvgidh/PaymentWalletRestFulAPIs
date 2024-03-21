package apprun.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import apprun.daos.BucketRepository;
import apprun.dtos.CreateTagRequestBody;
import apprun.models.Bucket;
import apprun.models.Wallet;

@Service
public class BucketService {

	@Autowired
	private BucketRepository bucketrepo;
	
	public String createtag(List<CreateTagRequestBody> body,String userid)  {
		// TODO Auto-generated method stub
			for(CreateTagRequestBody b:body)
			{
				Bucket save=Bucket.builder()
						.tag(b.getTag())
						.bucketlimit(b.getLimit())
						.amt(0D)
						.walletid("8793570758")
						.build();
				bucketrepo.save(save);
			}
			return "bucket list created";
	}
	

	public String updateBuckets(String tag,Wallet wallet,Double amount) {
		// TODO Auto-generated method stub
			Bucket b=getbucket(tag,wallet.getWalletid());
			Double currentamt=b.getAmt()+amount;
			b.setAmt(currentamt);
		    bucketrepo.save(b);
		    if(currentamt>b.getBucketlimit())
		    {
		    	return "bucket limit exceeded by "+(b.getAmt()-b.getBucketlimit())+" for tag "+b.getTag();
		    }
		    return "bucket updated for tag "+b.getTag();
			
	}
	private Bucket getbucket(String tag,String walletid)
	{
		return bucketrepo.getByTagAndWalletId(tag,walletid);
	}
	public HashMap<String, String> gettags(String userid)  {
		// TODO Auto-generated method stub
		List<Bucket> buckets=bucketrepo.findByWalletId("8793570758");
		HashMap<String,String> ret=new HashMap<String,String>();
		for(Bucket b:buckets)
		{
			ret.put(b.getTag(),"amt: "+ b.getAmt()+" ,limit: "+b.getBucketlimit());
		}
		return ret;
	}
}
