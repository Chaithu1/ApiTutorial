package com.linkedin.api.Twilio;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TwilioClientTest {

    @Value("${TWILIO_SID}")
    private String twilioSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String twilioAuthToken;

    @Value("${TO_NUMBER}")
    private String toNumber;

    private static final String fromNumber = "+12108532322";

    private static final String TWILIO_API_DOMAIN = "https://api.twilio.com";

    @Test
    public void twilioVoiceMessageTest(){
        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);

        TwilioClient client = Feign.builder()
                .requestInterceptor(interceptor)
                .encoder(new FormEncoder())
                .target(TwilioClient.class, TWILIO_API_DOMAIN);

        client.sendVoiceMessage(twilioSid, toNumber, fromNumber, "<Response><Say>Hey there Chaithu, this is your test. Have a nice day$EXCLAMATION_MARK</Say></Response>");

    }



}


