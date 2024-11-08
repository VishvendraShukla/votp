package com.votp.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.votp.exception.TwilioPropertiesNotProvidedException;
import com.votp.models.Notification;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("notification")
public class TwilioSmsNotifier implements Notifier {

  @Value("${TWILIO_ACCOUNT_SID:a}")
  private String twilioAccountSid;

  @Value("${TWILIO_AUTH_TOKEN:b}")
  private String twilioAuthToken;

  @Value("${TWILIO_SENDER_PHONE_NUMBER:c}")
  private String fromPhoneNumber;

  public TwilioSmsNotifier(
      @Value("${TWILIO_NOTIFIER_ENABLED:false}") Boolean isTwilioNotifierEnabled) {
    if (isTwilioNotifierEnabled) {
      log.info("TwilioSmsNotifier is enabled and is registered");
      register();
    }
  }

  @PostConstruct
  private void initializeTwilioWithProperties() {
    if (twilioAccountSid == null || twilioAccountSid.isEmpty()) {
      throw new TwilioPropertiesNotProvidedException("Account SID is not set.");
    }
    if (twilioAuthToken == null || twilioAuthToken.isEmpty()) {
      throw new TwilioPropertiesNotProvidedException("Account SID is not set.");
    }
    if (fromPhoneNumber == null || fromPhoneNumber.isEmpty()) {
      throw new TwilioPropertiesNotProvidedException("Sender phone number is not set.");
    }
    Twilio.init(twilioAccountSid, twilioAuthToken);
    log.info(
        "TwilioSmsNotifier is initialized with properties: twilioAccountSid: {}, twilioAuthToken: {}, fromPhoneNumber: {}",
        twilioAccountSid, twilioAuthToken, fromPhoneNumber);
  }

  @Override
  public void notify(Notification message) {
    Message sms = Message.creator(
            new PhoneNumber(message.getTo()),
            new PhoneNumber(fromPhoneNumber),
            message.getNotification())
        .create();
    log.info("Message SID: {}", sms.getSid());
  }
}
