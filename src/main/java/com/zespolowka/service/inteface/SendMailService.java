package com.zespolowka.service.inteface;

import com.zespolowka.entity.user.User;

/**
 * Created by Admin on 2016-02-17.
 */
public interface SendMailService {

    void sendVerificationMail(String url, User user);

    void sendReminderMail(User user);
}
