package AppRun.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import AppRun.dtos.CreateUserRequestData.CreateUserRequestDataBuilder;
import jakarta.validation.constraints.NotBlank;
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
public class AuthenticateUserRequestData {
	
	@NotBlank
	@JsonProperty("username")
	private String mobileno;
	
	@JsonProperty("password")
	private String password;
	
	

}
