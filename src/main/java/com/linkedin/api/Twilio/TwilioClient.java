package com.linkedin.api.Twilio;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TwilioClient {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /2010-04-01/Accounts/{AccountSid}/Calls.json")
    public void sendVoiceMessage(@Param("AccountSid") String accountSid,
                                 @Param("To") String to,
                                 @Param("From") String from,
                                 @Param("Twiml") String twiml);
}
