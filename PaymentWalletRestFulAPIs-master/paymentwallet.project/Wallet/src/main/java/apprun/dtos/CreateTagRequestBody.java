package apprun.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;

import apprun.models.Bucket;
import jakarta.validation.constraints.Min;
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
public class CreateTagRequestBody {
	
	@NotBlank
	@JsonProperty("tag")
	private String tag;
	
	@NotBlank
	@Min(100)
	@JsonProperty("limit")
	private Double Limit;
	
	
	


}
