package com.linkedin.api.Twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class TwitterStreamingService {
    @Value("${TWITTER_BEARER_TOKEN}")
    private String bearerToken;

    private static final String API_TWITTER_ENDPOINT = "https://api.twitter.com";
    private static final String API_TWITTER_STREAM_PATH = "/2/tweets/search/stream";

    @Autowired
    private WebClient.Builder builder;

    public Flux<String> stream() {
        WebClient client = builder
                .baseUrl(API_TWITTER_ENDPOINT)
                .defaultHeaders(headers -> headers.setBearerAuth(bearerToken))
                .build();

        return client.get()
                .uri(API_TWITTER_STREAM_PATH)
                .retrieve()
                .bodyToFlux(String.class)
                .filter(body -> !body.isBlank());
    }
}
