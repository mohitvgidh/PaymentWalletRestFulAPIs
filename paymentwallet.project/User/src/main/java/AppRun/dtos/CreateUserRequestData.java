package AppRun.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import AppRun.models.WalletUser;
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
	
	
	public WalletUser to()
	{
		return WalletUser.builder()
				.name(name)
				.mobileno(mobileno)
				.email(email)
				.build();
	}
	
}
