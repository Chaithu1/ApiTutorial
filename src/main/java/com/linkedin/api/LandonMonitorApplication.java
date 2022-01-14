package com.linkedin.api;

import com.linkedin.api.Azure.AzureSentimentService;
import com.linkedin.api.Azure.SentimentAnalysis;
import com.linkedin.api.Twitter.StreamResponse;
import com.linkedin.api.Twitter.TwitterStreamingService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Profile("!test")
@SpringBootApplication
public class LandonMonitorApplication implements CommandLineRunner {

    @Autowired
    private TwitterStreamingService twitterStreamingService;

    @Autowired
    private AzureSentimentService azureSentimentService;

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(LandonMonitorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        twitterStreamingService.stream()
                .subscribe(tweet -> {
                    try {
                        StreamResponse response = this.mapper().readValue(tweet, StreamResponse.class);

                        System.out.println(tweet);
                        SentimentAnalysis analysis = azureSentimentService.requestSentimentAnalysis(response.getData().getText(), "en");
                        System.out.println(analysis.getSentiment());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }

                });
    }
}
