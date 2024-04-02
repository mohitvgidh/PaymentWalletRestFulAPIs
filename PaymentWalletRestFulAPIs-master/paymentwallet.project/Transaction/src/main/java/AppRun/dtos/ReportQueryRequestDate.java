package AppRun.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportQueryRequestDate {

	@NotBlank
	@JsonProperty("fromdate")
	public String fromDate;
	
	@NotBlank
	@JsonProperty("todate")
	public String toDate;

	@JsonProperty("reason")
	public String reason;
	
	@JsonProperty("userid")
	public String userid;
}
