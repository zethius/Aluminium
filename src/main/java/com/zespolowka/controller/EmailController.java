package com.zespolowka.controller;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.CurrentUser;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.service.inteface.NotificationService;
import com.zespolowka.service.inteface.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Date;

/**
 * Created by Admin on 2016-02-17.
 * TODO
 * Do usuniecia pozniej - powstało tylko dla testow
 */

@RestController
public class EmailController {
    private SendMailService sendMailService;
    private NotificationService notificationService;

    @Autowired
    public EmailController(SendMailService sendMailService, NotificationService notificationService) {
        this.notificationService = notificationService;
        this.sendMailService = sendMailService;
    }

    @RequestMapping("/send-mail")
    public void sendMail() {
        sendMailService.sendSimpleMail("olsz72@o2.pl", "Prosty mail", "Wiadomosc testowa");
    }

    @RequestMapping("/send-mail2")
    public void sendMailWithAttachment() {
        sendMailService.sendMailWIthAttachment("olsz72@o2.pl", "Mail z zalacznikiem", "Wiadomosc testowa", new FileSystemResource(new File("D:/PRIR.pdf")));
    }

    @RequestMapping("/send-mail3")
    public void sendRichMail() {
        CurrentUser user = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sendMailService.sendRichMail("olsz72@o2.pl", "Ladniejszy mail", "Wiadomosc testowa", user.getUser());
    }

    @RequestMapping("/sendMessage")
    public void sendMessage() {
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User sender=currentUser.getUser();
        notificationService.createNotification(new Notification("GRUPOWA", "Temat", new Date(), Role.ADMIN,sender));
    }

    @Override
    public String toString() {
        return "EmailController{" +
                "sendMailService=" + sendMailService +
                ", notificationService=" + notificationService +
                '}';
    }
}
