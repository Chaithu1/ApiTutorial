package com.linkedin.api.Azure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class AzureSentimentService {

    @Value("${AZURE_API_KEY}")
    private String azureApiKey;

    private static final String AZURE_ENDPOINT = "https://landon-hotel-feedback-tutorial.cognitiveservices.azure.com/";
    private static final String AZURE_ENDPOINT_PATH = "/text/analytics/v3.0/sentiment";
    private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    @Autowired
    public ObjectMapper mapper;


    public SentimentAnalysis requestSentimentAnalysis(String text, String language) throws IOException, InterruptedException {

        TextDocument document = new TextDocument("1", text, language);
        TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
        requestBody.getDocuments().add(document);

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .proxy(ProxySelector.getDefault())
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .headers(API_KEY_HEADER_NAME, azureApiKey)
                .uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody)))
                .timeout(Duration.ofSeconds(5))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println(response.body());
            throw new RuntimeException("An issue occurred making the API call");
        }

        String value = this.mapper
                .readValue(response.body(), JsonNode.class)
                .get("documents")
                .get(0)
                .get("sentiment")
                .asText();
        return new SentimentAnalysis(document, value);
    }
}
