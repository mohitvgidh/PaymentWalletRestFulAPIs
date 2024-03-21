package apprun.models;


import java.util.Date;


import org.hibernate.annotations.CreationTimestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bucket {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false)
	private String tag;
	
	@Column(nullable=false)
	private Double bucketlimit;
	
	@Column(nullable=false)
	private Double amt;
	

	@CreationTimestamp
	private Date CreatedDate;
	
	@Column(nullable=true)
	private Date DeletedDate;
	
	@Column(nullable=false)
	private String walletid;
	
	
	
	

	
}
