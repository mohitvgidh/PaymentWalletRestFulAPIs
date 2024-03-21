package apprun.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.annotations.*;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false,unique=true)
	private String walletid;

	@Column(nullable=false)
    private Double balance;
	
	@Column(nullable=false)
	private String currency;
	
	@CreationTimestamp
	private Date createdOn;
	
	@UpdateTimestamp
	private Date updatedOn;
	

	

	
	
	

}
