package AppRun.dtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;

import AppRun.models.WalletUser;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestData {
	
	
	@NotBlank
	@JsonProperty("name")
	private String name;
	
	@NotBlank
	@JsonProperty("mobile")
	private String mobileno;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("roles")
	private String roles;
	
	@JsonProperty("password")
	private String password;
	
	
	public WalletUser to()
	{
		return WalletUser.builder()
				.name(name)
				.mobileno(mobileno)
				.email(email)
				.roles(roles)
				.password(password)
				.build();
	}
	
}
