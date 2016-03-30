package com.zespolowka.service;

import com.zespolowka.entity.user.User;
import com.zespolowka.service.inteface.SendMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * Created by Pitek on 2016-02-17.
 */
@Service
public class SendMailServiceImpl implements SendMailService {
    private static final Logger logger = LoggerFactory.getLogger(SendMailService.class);

    private final JavaMailSender mailSender;
    private final MimeMessage mimeMessage;
    private MimeMessageHelper message;

    @Autowired
    public SendMailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        mimeMessage = mailSender.createMimeMessage();
    }

    ///do testow - do usuniecia pozniej
    public void sendSimpleMail(String to, String subject, String body) {
        try {
            message = new MimeMessageHelper(mimeMessage);
            message.setFrom("noreply@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(mimeMessage);
            logger.info("Wysylam prostego maila");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    ///do testow - do usuniecia pozniej
    public void sendMailWIthAttachment(String to, String subject, String body, FileSystemResource file) {
        try {
            message = new MimeMessageHelper(mimeMessage, true);
            message.setFrom("noreply@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.addAttachment(file.getFilename(), file);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    ///do testow - do usuniecia pozniej
    public void sendRichMail(String to, String subject, String body, User user) {
        try {
            message = new MimeMessageHelper(mimeMessage, true);
            message.setFrom("noreply@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText("<html><body>" +
                    "<h4>Witaj, " + user.getName() + " " + user.getLastName() + "</h4>" +
                    "<i>" + body + "</i>" +
                    "</body></html>", true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void sendVerificationMail(String url, User user) {
        try {
            message = new MimeMessageHelper(mimeMessage, true);
            message.setFrom("noreply@gmail.com");
            message.setTo(user.getEmail());
            message.setSubject("Confirm Registration");
            message.setText("<html><body>" +
                    "<h4>Witaj, " + user.getName() + " " + user.getLastName() + "</h4>" +
                    "<i>" + url + "</i>" +
                    "</body></html>", true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
