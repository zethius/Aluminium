package com.zespolowka.controller;

import com.zespolowka.Service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Peps on 2016-02-27.
 * Controller majacy wyswietlic strone glowna - na razie nic nie robi
 */
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final NotificationService notificationService;

    @Autowired
    public MainController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/main")
    public String mainPage(Model model) {
        logger.info("nazwa metody = main");
        try {
            model.addAttribute("Notifications", notificationService.getAllNotifications());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(model.toString() + "\n" + notificationService.getAllNotifications().toString());
        }
        return "main";
    }
}
