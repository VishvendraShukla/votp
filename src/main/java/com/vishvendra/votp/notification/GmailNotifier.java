package com.vishvendra.votp.notification;

import com.vishvendra.votp.exception.GmailPropertiesNotProvidedException;
import com.vishvendra.votp.models.Notification;
import jakarta.annotation.PostConstruct;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("notification")
@ConditionalOnProperty(name = "GMAIL_NOTIFIER_ENABLED", havingValue = "true", matchIfMissing = false)
public class GmailNotifier implements Notifier {

  @Value("${GMAIL_HOST:smtp.gmail.com}")
  private String gmailHost;
  @Value("${GMAIL_PORT:587}")
  private Long gmailPort;
  @Value("${GMAIL_USERNAME:}")
  private String gmailUsername;
  @Value("${GMAIL_PASSWORD:}")
  private String gmailPassword;
  @Value("${GMAIL_SMTP_AUTH:true}")
  private Boolean smtpAuth;
  @Value("${GMAIL_START_TLS_ENABLED:true}")
  private Boolean startTlsEnabled;
  @Value("${GMAIL_SSL_TRUST:true}")
  private Boolean sslTrust;
  @Value("${GMAIL_FROM_EMAIL:}")
  private String fromEmail;
  @Value("${GMAIL_SUBJECT:OTP}")
  private String subject;

  private JavaMailSender mailSender;

  public GmailNotifier() {
    register();
  }

  @Override
  public void notify(Notification message) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(message.getTo().getEmail());
    email.setSubject(subject);
    email.setText(message.getNotification());
    email.setFrom(fromEmail);
    this.mailSender.send(email);
    log.info("Email sent successfully to: {}", message.getTo());
  }

  @PostConstruct
  private void initializeGmailWithProperties() {
    if (gmailUsername == null || gmailUsername.isEmpty()) {
      throw new GmailPropertiesNotProvidedException("Username is not set.");
    }
    if (gmailPassword == null || gmailPassword.isEmpty()) {
      throw new GmailPropertiesNotProvidedException("Password is not set.");
    }
    if (fromEmail == null || fromEmail.isEmpty()) {
      fromEmail = gmailUsername;
      log.info("From email is not set. Using gmail username as from email. {}", fromEmail);
    }
    this.mailSender = getJavaMailSender();
    log.info(
        "GmailNotifier is enabled and is initialized with properties: username:{}, password: XXXXXXXXX, from email: {}, subject: {}",
        gmailUsername, fromEmail, subject);
  }

  private JavaMailSenderImpl getJavaMailSender() {
    Properties mailProperties = new Properties();
    mailProperties.put("mail.smtp.host", gmailHost);
    mailProperties.put("mail.smtp.port", gmailPort);
    mailProperties.put("mail.smtp.auth", smtpAuth);
    mailProperties.put("mail.smtp.starttls.enable", startTlsEnabled);
    mailProperties.put("mail.smtp.ssl.trust", sslTrust);

    JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
    javaMailSenderImpl.setHost(gmailHost);
    javaMailSenderImpl.setPort(gmailPort.intValue());
    javaMailSenderImpl.setUsername(gmailUsername);
    javaMailSenderImpl.setPassword(gmailPassword);
    javaMailSenderImpl.setJavaMailProperties(mailProperties);
    return javaMailSenderImpl;
  }
}
