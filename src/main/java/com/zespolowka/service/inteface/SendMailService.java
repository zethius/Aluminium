package com.zespolowka.service.inteface;

import com.zespolowka.entity.user.User;
import org.springframework.core.io.FileSystemResource;

/**
 * Created by Admin on 2016-02-17.
 */
public interface SendMailService {
    public void sendSimpleMail(String to, String subject, String body);

    public void sendMailWIthAttachment(String to, String subject, String body, FileSystemResource file);

    public void sendRichMail(String to, String subject, String body, User user);

    public void sendVerificationMail(String url, User user);

    public void sendReminderMail(User user);
}
