package com.ner.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ner.config.ChatConfig;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.CoreMapExpressionExtractor;
import edu.stanford.nlp.ling.tokensregex.Env;
import edu.stanford.nlp.ling.tokensregex.MatchedExpression;
import edu.stanford.nlp.ling.tokensregex.NodePattern;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.ErasureUtils;

@Service
public class NlpService<T> {

	
	private StanfordCoreNLP pipeline;
	
	private ChatConfig chatconfig;
	
	@Autowired
	public NlpService(StanfordCoreNLP pipeline,ChatConfig chatconfig) {
		this.pipeline=pipeline;
		this.chatconfig=chatconfig;
	}
	
	// My custom tokens
	  public static class MyTokensAnnotation implements CoreAnnotation<List<? extends CoreMap>> {
	    @Override
	    public Class<List<? extends CoreMap>> getType() {
	      return ErasureUtils.<Class<List<? extends CoreMap>>> uncheckedCast(List.class);
	    }
	  }

	  // My custom type
	  public static class MyTypeAnnotation implements CoreAnnotation<String> {
	    @Override
	    public Class<String> getType() {
	      return ErasureUtils.<Class<String>> uncheckedCast(String.class);
	    }
	  }

	  // My custom value
	  public static class MyValueAnnotation implements CoreAnnotation<String> {
	    @Override
	    public Class<String> getType() {
	      return ErasureUtils.<Class<String>> uncheckedCast(String.class);
	    }
	  }
	  
	 private T appName;
	 private T appOwner;
	 private T jokes;
	 private T appStatus;
	 private T personName;
	 private T greetUser;
	 private T cityName;
	
	 private Random random;
	 
	public Map<String,T> parseMessage(String message){
		Map<String, T> map=new HashMap<String,T>();
		
		Annotation exampleSentencesAnnotation = new Annotation(message);
	    pipeline.annotate(exampleSentencesAnnotation);

	    String[] rulesFiles = chatconfig.getRules().split(",");
	    
	    //Case insenstive Setup
	    Env env = TokenSequencePattern.getNewEnv();
	    env.setDefaultStringMatchFlags(NodePattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	    env.setDefaultStringPatternFlags(Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	    
	 // build the CoreMapExpressionExtractor
	    CoreMapExpressionExtractor extractor = CoreMapExpressionExtractor.createExtractorFromFiles(env, rulesFiles);
	    
	    // for each sentence in the input text, run the TokensRegex pipeline
	    int sentNum = 0;
	    
	    for (CoreMap sentence : exampleSentencesAnnotation.get(CoreAnnotations.SentencesAnnotation.class)) {
	    	sentNum++;
	    	
	    	 System.out.println("sentence text: "+ sentence.get(CoreAnnotations.TextAnnotation.class));
	    	 List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);
	    	 
	    	 for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
	    	  //      System.out.println(token.word() + "\t" + token.tag() + "\t" + token.ner());
	    	  
	    	 
	    	        if(token.ner().equalsIgnoreCase("PERSON") && token.tag().equalsIgnoreCase("NNP")) {
	    	        	personName=(T) token.word();
	    	        }else if(token.ner().equalsIgnoreCase("CITY") && token.tag().equalsIgnoreCase("NNP")) {
	    	        	cityName=(T) token.word();
	    	        }else if(token.ner().equalsIgnoreCase("GREET_USER")) {
	    	        	greetUser=(T) Boolean.TRUE;
	    	        	
	    	        }else if(token.ner().equalsIgnoreCase("JOKES")) {
	    	        	jokes=(T) Boolean.TRUE;
	    	        }else if(token.ner().equalsIgnoreCase("APP_NAME")) {
	    	        	appName= (T) token.word();
	    	        }else if(token.ner().equalsIgnoreCase("APP_STATUS")) {
	    	        	appStatus=(T) Boolean.TRUE;
	    	        }else if(token.ner().equalsIgnoreCase("APP_OWENR")) {
	    	        	appOwner=(T) Boolean.TRUE;
	    	        }
	    	 
	    	 }
	    	 
	    	 map.put("person", personName);
	    	 map.put("city", cityName);
	    	
	    	 map.put("greet", greetUser);
	    	 map.put("jokes", jokes);
	    	 
	    	 map.put("appName", appName);
	    	 map.put("appStatus", appStatus);
	    	 
	    	 map.put("appOwner", appOwner);

	    	 
	    	 // print out the matched expressions
	         for (MatchedExpression me : matchedExpressions) {
	           System.out.println("matched expression: "+me.getText());
	           System.out.println("matched expression value: "+me.getValue());
	           
	         }
	    	 	    	
	    }
	    
		return map;
		
	}
	
	
	public String jokeMessage() {
		
		random=new Random();
		int number=random.nextInt(2 - 0 + 1) + 0;
		return chatconfig.getJokes().split("\\|")[number].toString();
	}
	
	
	public String greetMessage() {
		random=new Random();
		int number=random.nextInt(2 - 0 + 1) + 0;
		return chatconfig.getGreetMessages().split("\\|")[number].toString();
	}
	
}
