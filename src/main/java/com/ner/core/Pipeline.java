package com.ner.core;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Configuration
public class Pipeline {

    private static Properties properties;
    private static String propertiesName = "tokenize, ssplit, pos, lemma, ner,tokensregex";
    private static StanfordCoreNLP stanfordCoreNLP;

   
    static {
        properties = new Properties();
        properties.setProperty("annotators", propertiesName);
    }

    @Bean(name = "stanfordCoreNLP")
    public static StanfordCoreNLP getInstance() {
        if(stanfordCoreNLP == null) {
            stanfordCoreNLP = new StanfordCoreNLP(properties);
        }
        return stanfordCoreNLP;
    }
}
