package com.linkedin.api.Twitter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TwitterSearchTests {

    @Value("${TWITTER_BEARER_TOKEN}")
    private String bearerToken;

    private static final String API_TWITTER_ENDPOINT = "https://api.twitter.com";
    private static final String API_TWITTER_ENDPOINT_PATH = "/2/tweets/search/recent";

    @Test
    void webClientTest() throws InterruptedException {
        WebClient client = WebClient.create(API_TWITTER_ENDPOINT);

        Mono<ResponseEntity<String>> mono = client.get()
                .uri(API_TWITTER_ENDPOINT_PATH + "?query={query}", "LinkedIn Learning")
                .header("Authorization", "Bearer " + this.bearerToken)
                .retrieve()
                .toEntity(String.class);
//
//        ResponseEntity<String> response = mono.block();
//
//        System.out.println(response.getBody());
//        assertEquals(200, response.getStatusCodeValue());

        mono.subscribe(response -> {
            System.out.println(response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        });

        System.out.println("This should print before cuz async");
        Thread.sleep(5000);

    }
}
