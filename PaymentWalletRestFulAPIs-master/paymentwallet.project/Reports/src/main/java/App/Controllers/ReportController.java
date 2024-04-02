package App.Controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import App.Model.Report;
import App.Model.Transaction;
import App.Services.ReportService;
import App.dtos.ReportRequest;
import jakarta.validation.Valid;

@RestController
public class ReportController {
	
	@Autowired
	ReportService reportservice;
	
	@GetMapping("/report/get")
	public List<Report> getAllReports()
	{
		return null;
	}
	@PostMapping("/report/txn")
	public List<Transaction> getTransactions(@RequestBody @Valid ReportRequest query) throws ParseException
	{
		return reportservice.gettransactions(query);
	}
	@GetMapping("/report/generate")
	public Report generateReport(@RequestBody @Valid ReportRequest query) throws ParseException
	{
		return reportservice.generateReport(query);
	}
	


}
