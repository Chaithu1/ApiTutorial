package com.linkedin.api.Azure;

import java.io.IOException;

import com.linkedin.api.Twilio.TwilioClient;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AzureNamedEntitiesTest {

	@Value("${AZURE_API_KEY}")
	private String azureApiKey;

	@Value("${TWILIO_SID}")
	private String twilioSid;

	@Value("${TWILIO_AUTH_TOKEN}")
	private String twilioAuthToken;

	@Value("${TO_NUMBER}")
	private String toNumber;

	@Value("${Path}")
	private String path;
	private static final String fromNumber = "+12108532322";

	private static final String TWILIO_API_DOMAIN = "https://api.twilio.com";

	private static final String AZURE_ENDPOINT = "https://landon-hotel-feedback-tutorial.cognitiveservices.azure.com/";

	private static final String AZURE_ENDPOINT_PATH = "/text/analytics/v3.0/entities/recognition/general";

	private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String APPLICATION_JSON = "application/json";

	private static final String EXAMPLE_JSON  = "{"
			+ "  \"documents\": ["
			+ "    {"
			+ "      \"language\": \"en\","
			+ "      \"id\": \"1\","
			+ "      \"text\": \"The Landon Hotel was found in 1952 London by Arthur Landon after World War II.\""
			+ "    }"
			+ "  ]"
			+ "}";


	@Test
	public void getEntities() throws IOException, InterruptedException {
//
//		System.out.println(path);
//		System.out.println();
//		// 1.  Create a client
//		HttpClient client = HttpClient.newHttpClient();
//		// 2.  Create the request
//		HttpRequest request = HttpRequest.newBuilder()
//				.header(CONTENT_TYPE, APPLICATION_JSON)
//				.headers(API_KEY_HEADER_NAME, azureApiKey)
//				.uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
//				.POST(HttpRequest.BodyPublishers.ofString(EXAMPLE_JSON))
//				.build();
//		// 3.  Send the request and receive response
//		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//		// 4.  Work with the response
//		assertEquals(200, response.statusCode());
//		System.out.println(response.body());

		System.out.println(path);
		BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);

		TwilioClient client = Feign.builder()
				.requestInterceptor(interceptor)
				.encoder(new FormEncoder())
				.target(TwilioClient.class, TWILIO_API_DOMAIN);

		client.sendVoiceMessage(twilioSid, toNumber, fromNumber, "<Response><Say>Hey there Chaithu, this is your test. Have a nice day$EXCLAMATION_MARK</Say></Response>");

	}



}


