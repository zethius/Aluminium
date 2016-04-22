package com.zespolowka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by Admin on 2016-02-17.
 */
@Configuration
public class MailConfig {
<<<<<<< HEAD
    public MailConfig() {
    }

=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("allustela@gmail.com");
        mailSender.setPassword("zaq1@WSX1");

        Properties prop = mailSender.getJavaMailProperties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.debug", "true");
        return mailSender;
    }
}
