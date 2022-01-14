package com.linkedin.api.Azure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;



@SpringBootTest
public class AzureSentimentServiceTest {

    @Autowired
    private AzureSentimentService sentimentService;

    @Test
    void testPositiveSentiment() throws IOException, InterruptedException {
        SentimentAnalysis analysis = this.sentimentService.requestSentimentAnalysis("I love landon Hotel!", "en");
        assertNotNull(analysis);
        assertEquals("positive", analysis.getSentiment());
    }

    @Test
    void testNegativeSentiment() throws IOException, InterruptedException {
        SentimentAnalysis analysis = this.sentimentService.requestSentimentAnalysis("Landon Hotel is horrible!", "en");
        assertNotNull(analysis);
        assertEquals("negative", analysis.getSentiment());
    }
}
