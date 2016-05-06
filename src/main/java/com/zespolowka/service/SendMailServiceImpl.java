package com.zespolowka.service;

import com.zespolowka.entity.user.User;
import com.zespolowka.service.inteface.SendMailService;
import com.zespolowka.service.inteface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;

/**
 * Created by Pitek on 2016-02-17.
 */
@Service
public class SendMailServiceImpl implements SendMailService {
    private static final Logger logger = LoggerFactory.getLogger(SendMailService.class);

    private final JavaMailSender mailSender;
    private final MimeMessage mimeMessage;
    private MimeMessageHelper message;
    private final UserService userService;
    @Autowired
    public SendMailServiceImpl(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        mimeMessage = mailSender.createMimeMessage();
        this.userService=userService;
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
        } catch (MessagingException e) {
            e.printStackTrace();
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

        } catch (MessagingException e) {
            e.printStackTrace();
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
                    "<h4>Witaj, " + user.getName() + ' ' + user.getLastName() + "</h4>" +
                    "<i>" + body + "</i>" +
                    "</body></html>", true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
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
                    "<h4>Witaj, " + user.getName() + ' ' + user.getLastName() + "</h4>" +
                    "<i>" + url + "</i>" +
                    "</body></html>", true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendReminderMail(User user) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        try {
            message = new MimeMessageHelper(mimeMessage, true);
            message.setFrom("noreply@gmail.com");
            message.setTo(user.getEmail());
            message.setSubject("Przypomnienie Hasła");
            String newPassword = sb.toString();
            user.setPasswordHash(new BCryptPasswordEncoder().encode(newPassword));
            userService.update(user); //dodane
            message.setText("<html><body><h4>Witaj " + user.getName() + "!</h4><p>Twoje nowe hasło to: " + newPassword + "</p></body></html>", true);
            mailSender.send(mimeMessage);
            logger.info("Reminder sent", newPassword);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "SendMailServiceImpl{" +
                "mailSender=" + mailSender +
                ", mimeMessage=" + mimeMessage +
                ", message=" + message +
                '}';
    }
}
