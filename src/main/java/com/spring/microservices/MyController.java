package com.spring.microservices;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class MyController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/")
	public String redirectUrl() {
		return "index";
	}
	
	@RequestMapping("/users")
	@ResponseBody
	public List<Object> userService() {
		System.out.println("Fetching details....");
		String url = "http://userservice-env.eba-rnibmpmc.us-east-2.elasticbeanstalk.com/getRegUserDetails";
		ResponseEntity<Object[]> regDetails = restTemplate.getForEntity(url, Object[].class);
		return Arrays.asList(regDetails.getBody());
	}
}
