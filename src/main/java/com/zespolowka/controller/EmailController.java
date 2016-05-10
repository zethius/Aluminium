package com.zespolowka.controller;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.CurrentUser;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.service.inteface.NotificationService;
import com.zespolowka.service.inteface.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
public class EmailController {
    private SendMailService sendMailService;
    private NotificationService notificationService;

    @Autowired
    public EmailController(SendMailService sendMailService, NotificationService notificationService) {
        this.notificationService = notificationService;
        this.sendMailService = sendMailService;
    }

    @RequestMapping("/sendMessage")
    public void sendMessage() {
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User sender = currentUser.getUser();
        notificationService.createNotification(new Notification("GRUPOWA", "Temat", new Date(), Role.ADMIN, sender));
    }

    @Override
    public String toString() {
        return "EmailController{" +
                "sendMailService=" + sendMailService +
                ", notificationService=" + notificationService +
                '}';
    }
}
