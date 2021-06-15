package com.ner.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ner.service.NlpService;


@RestController
@RequestMapping(value = "/api/v1")
public class NERController {

	NlpService nlpservice;

	@Autowired
	public NERController(NlpService nlpservice) {
		this.nlpservice=nlpservice;
	}
    
	Map<String, Object> mapResponse=new HashMap<String,Object>();
	
	@RequestMapping(value="/botrequest", method=RequestMethod.POST)
	@ResponseBody
	public String botResponse(@RequestBody final String message) {
		mapResponse=nlpservice.parseMessage(message.toUpperCase());
		mapResponse.forEach((k,v) -> System.out.println(k + " " + v));
		
		 String name;
		 String greet;
		 String appname;
		 String city;
		 String defaultresponse;
		
		
		
		
		if(mapResponse.get("person")!=null && mapResponse.get("person").toString().length() > 1) {
			name=mapResponse.get("person").toString();
			
		}else {
			name="Unknown !!!";
		}
		
		if(mapResponse.get("city") != null && mapResponse.get("city").toString().length() > 1) {
			city=mapResponse.get("city").toString();
			
		}
		
		if(mapResponse.get("greet")!= null && (boolean) mapResponse.get("greet")) {
			greet=nlpservice.greetMessage();
			
		}else {
			greet="Hey";
		}
		
		
		
		if(mapResponse.get("appName")!=null && mapResponse.get("appName").toString().length() > 1) {
			appname=(String) mapResponse.get("appName");
			
		}else {
			appname="App1";
		}
		
		if(mapResponse.get("jokes")!=null && (boolean) mapResponse.get("jokes")) {
			defaultresponse=nlpservice.jokeMessage();
			
		} else if( mapResponse.get("appOwner") != null && (boolean) mapResponse.get("appOwner")) {
			defaultresponse="Owner for application " + appname + " is : <h3>\"Sujeet\"</h3>";
			
		}else if(mapResponse.get("appStatus") !=null && (boolean) mapResponse.get("appStatus")) {
			defaultresponse="Status for " + appname + " is : <h3>\"Active\"</h3>";
			
		}else {
			defaultresponse="how can we help  you!!!!!";
		}
		
		return greet + " " + name + "," + " " + defaultresponse;
	}
	
}
