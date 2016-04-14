package com.zespolowka.service.inteface;

import com.zespolowka.entity.user.User;
import org.springframework.core.io.FileSystemResource;

/**
 * Created by Admin on 2016-02-17.
 */
public interface SendMailService {
    void sendSimpleMail(String to, String subject, String body);

    void sendMailWIthAttachment(String to, String subject, String body, FileSystemResource file);

    void sendRichMail(String to, String subject, String body, User user);

    void sendVerificationMail(String url, User user);

    void sendReminderMail(User user);
}
