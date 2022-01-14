package com.linkedin.api.Azure;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AzureKeyPhraseTest {
    @Value("${AZURE_API_KEY}")
    private String azureApiKey;

    private static final String AZURE_ENDPOINT = "https://landon-hotel-feedback-tutorial.cognitiveservices.azure.com/";

    private static final String AZURE_ENDPOINT_PATH = "/text/analytics/v3.0/keyPhrases";

    private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String APPLICATION_JSON = "application/json";

    private static final String EXAMPLE_JSON  = "{"
            + "  \"documents\": ["
            + "    {"
            + "      \"language\": \"en\","
            + "      \"id\": \"1\","
            + "      \"text\": \"In an e360 interview, Carlos Nobre, Brazil’s leading expert on the Amazon and climate change, "
            + "discusses the key perils facing the world’s largest rainforest, where a record number of fires are now raging, "
            + "and lays out what can be done to stave off a ruinous transformation of the region.\""
            + "    }"
            + "  ]"
            + "}";

    private static final String textForAnalysis = "In an e360 interview, Carlos Nobre, Brazil’s leading expert on the Amazon and climate change, discusses the key perils facing the world’s largest rainforest, where a record number of fires are now raging, and lays out what can be done to stave off a ruinous transformation of the region.";

    @Autowired
    public ObjectMapper mapper;


    @Test
    public void getKeyPhrases() throws IOException, InterruptedException {

        TextDocument document = new TextDocument("1", textForAnalysis, "en");
        TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
        requestBody.getDocuments().add(document);

        // 1.  Create a client
        HttpClient client = HttpClient.newHttpClient();
        // 2.  Create the request
        HttpRequest request = HttpRequest.newBuilder()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .headers(API_KEY_HEADER_NAME, this.azureApiKey)
                .uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody)))
                .build();
        // 3.  Send the request and receive response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(response -> response.body())
        .thenAccept(body -> {
            JsonNode node;
            try{
                node = mapper.readValue(body, JsonNode.class);
                String value = node.get("documents")
                        .get(0)
                        .get("keyPhrases")
                        .get(0)
                        .asText();
                System.out.println("The first key phrase is " + value);
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
        });

        System.out.println("This will print first");
        Thread.sleep(5000);
    }
}
