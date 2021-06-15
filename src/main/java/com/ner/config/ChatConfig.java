package com.ner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
	
	@Value("${ruleFiles}")
	private String rules;

	@Value("${greet.messages}")
	private String greetMessages;
	
	@Value("${oneline.jokes}")
	private String jokes;

	public String getRules() {
		return rules;
	}

	public String getGreetMessages() {
		return greetMessages;
	}

	public String getJokes() {
		return jokes;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public void setGreetMessages(String greetMessages) {
		this.greetMessages = greetMessages;
	}

	public void setJokes(String jokes) {
		this.jokes = jokes;
	}
	
	
	
}
