package com.spring.microservices.controller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.spring.microservices.bean.Person;
import com.spring.microservices.bean.Shop;
import com.spring.microservices.bean.ShopAddress;
import com.spring.microservices.bean.ShopProduct;
import com.spring.microservices.bean.Student;
import com.spring.microservices.repository.MyRepository;
import com.spring.microservices.repository.MyRepositoryPerson;
import com.spring.microservices.repository.MyRepositoryShop;

@Controller
public class MyController {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	MyRepository myRepo;
	
	@Autowired
	MyRepositoryShop myRepoShop;

	@Autowired
	MyRepositoryPerson myRepoPerson;
	
	MongoTemplate mongoTemplate;
	
	@Autowired
	public MyController(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

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
		s1.setId(103);
		s1.setStudentName("PK Bose");
		s1.setStudentAddress("Haryana");
		s1.setMarks(65);
		
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
	
	@RequestMapping("/mongo/insertShop")
	@ResponseBody
	public String insertShopDetails() {
		
		ShopAddress adrs = new ShopAddress();
		adrs.setCity("Kolkata");
		adrs.setCountry("India");
		
		ShopProduct product = new ShopProduct();
		product.setProductId(1011);
		product.setProductName("Burnol");
		product.setProductPrice(175f);
		ShopProduct product1 = new ShopProduct();
		product1.setProductId(2022);
		product1.setProductName("Water Bottle");
		product1.setProductPrice(55f);
		List<ShopProduct> shopProductList = new ArrayList<>();
		shopProductList.add(product);
		shopProductList.add(product1);
		
		Shop shop = new Shop();
		shop.setShopId(101);
		shop.setShopName("OK Store");
		shop.setShopOwnerName("Owner Bro");
		shop.setShopAddress(adrs);
		shop.setShopProductList(shopProductList);
		
		myRepoShop.save(shop);
		
		return "ok!!";
	}
	
	@RequestMapping("/mongo/fetchShop")
	@ResponseBody
	public Shop fetchShopDetails() {
		Shop shop = myRepoShop.findAll().get(0);
		
		return mongoTemplate.findAll(Shop.class).get(0);
	}
	
	@RequestMapping("/mongo/insertPersons")
	@ResponseBody
	public String insertPersons() {
		for(int i=1; i<=1000; i++) {
			Person p = new Person();
			p.setId(i);
			p.setGender("Male");
			p.setName("Name"+i);
			p.setLanguage("Lang"+i);
			
			myRepoPerson.save(p);
		}
		for(int i=1001; i<=2000; i++) {
			Person p = new Person();
			p.setId(i);
			p.setGender("Female");
			p.setName("Name"+i);
			p.setLanguage("Lang"+i);
			
			myRepoPerson.save(p);
		}
		
		return "ok";
	}
}
