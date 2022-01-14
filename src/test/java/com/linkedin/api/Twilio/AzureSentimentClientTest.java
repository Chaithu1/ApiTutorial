package com.linkedin.api.Twilio;

import com.linkedin.api.Azure.SentimentAnalysisResponse;
import com.linkedin.api.Azure.TextAnalyticsRequest;
import com.linkedin.api.Azure.TextDocument;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
public class AzureSentimentClientTest {
    @Value("${AZURE_API_KEY}")
    private String azureApiKey;

    private static final String AZURE_ENDPOINT = "https://landon-hotel-feedback-tutorial.cognitiveservices.azure.com/";

    @Test
    void testFeignPositiveSentiment() throws IOException, InterruptedException {
        TextDocument document = new TextDocument("1", "I love the Landon Hotel", "en");
        TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
        requestBody.getDocuments().add(document);

        SentimentAnalysisResponse analysis = null;

        AzureSentimentClient client = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(AzureSentimentClient.class, AZURE_ENDPOINT);

                analysis = client.analyze(azureApiKey, requestBody);

        assertNotNull(analysis);
        assertEquals("positive", analysis.getDocuments().get(0).getSentiment());
    }
}
