package com.zespolowka.controller;

import com.zespolowka.entity.Notification;
import com.zespolowka.forms.NewMessageForm;
import com.zespolowka.service.inteface.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    @Autowired
    private NotificationService notificationService;


    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String showNotifications(final Model model, @ModelAttribute("Notification") final Notification notification) {
        logger.info("nazwa metody = showNotifications");
        try {
            model.addAttribute("idNotification", notification.getId());
            logger.info(notification.getId() + "");

        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "messages";
    }

    @RequestMapping(value = "/messages/{id}", method = RequestMethod.GET)
    public String readNotification(@PathVariable final Integer id, Model model, final RedirectAttributes redirectAttributes) {
        logger.info("nazwa metody = readNotification");
        Notification notif = notificationService.getNotificationById(id.longValue());
        notif.setUnread(false);
        notificationService.createNotification(notif);
        redirectAttributes.addFlashAttribute("Notification", notif);
        return "redirect:/messages";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public String newMessage(final Model model) {
        logger.info("nazwa metody = newMessage");
        model.addAttribute("newMessageForm", new NewMessageForm());
        return "sendMessage";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public String sendMessage(final Model model, @ModelAttribute final NewMessageForm newMessageForm) {
        logger.info("nazwa metody = sendMessage");
        try {
            notificationService.sendMessage(newMessageForm);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            logger.info("\n" + model + "\n");
        }
        return "sendMessage";
    }
}