package apprun.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import apprun.dtos.CreateTagRequestBody;
import apprun.models.Bucket;
import apprun.services.BucketService;
import jakarta.validation.Valid;

@RestController
public class BucketController {
	
	@Autowired
	private BucketService bucketservice;
	
	@GetMapping("/gettags")
	
	public HashMap<String, String> gettags(String userid) throws ClassNotFoundException, IOException
	{
		return bucketservice.gettags(userid);
	}
	@PostMapping("/createtag")
	
	public String createtag(@RequestBody  @Valid List<CreateTagRequestBody> body,String userid) {
		return bucketservice.createtag(body,userid);
	}

}
