package com.spring.microservices.controller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.spring.microservices.bean.Student;
import com.spring.microservices.repository.MyRepository;

@Controller
public class MyController {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	MyRepository myRepo;


	@RequestMapping("/")
	public String redirectUrl() {
		return "index";
	}

	@RequestMapping("/users")
	@ResponseBody
	public List<Object> userService() {
		System.out.println("Fetching details....");
		String url = "http://ec2-13-59-44-236.us-east-2.compute.amazonaws.com:8080/user-service/getRegUserDetails";
		ResponseEntity<Object[]> regDetails = restTemplate.getForEntity(url, Object[].class);
		return Arrays.asList(regDetails.getBody());
	}

	@RequestMapping("/mongo/insertStudent")
	@ResponseBody
	public String insertStudent() {
		Student s1 = new Student();
		s1.setId(101);
		s1.setStudentName("DK Bose");
		s1.setStudentAddress("Delhi");
		s1.setMarks(75);
		
		myRepo.save(s1);
		
		return "Student inserted";
	}
	
	@RequestMapping("/mongo/fetchStudents")
	@ResponseBody
	public List<Student> fetchAllStudents() {
		
		return myRepo.findAll();
	}
	
	@RequestMapping("/time")
	@ResponseBody
	public String getLocalTime() {
		DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		ZoneId destinationZI = ZoneId.of("Asia/Kolkata");
		ZoneId sourceZI = ZoneId.of("Etc/GMT");
		
		Date d = new Date();
		LocalDateTime ld = LocalDateTime.parse("2020-07-01 07:50:00",formatter);
		
		ZonedDateTime sourceDT = ld.atZone(sourceZI);
		ZonedDateTime destinationDT = sourceDT.withZoneSameInstant(destinationZI);
		
		System.out.println("Source: "+sourceDT);
		System.out.println("Destination: "+destinationDT);
		
		/***************************************************************************/
		
		Date d1 = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone tz = TimeZone.getTimeZone("GMT");
		df.setTimeZone(tz);
		System.out.println(d);
		try {
			System.out.println(df1.parse(df.format(d)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "ok";
		
	}
}
