package com.zespolowka.controller;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.CurrentUser;
import com.zespolowka.entity.user.User;
import com.zespolowka.service.inteface.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;


@Controller
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private HttpSession httpSession;

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String showNotifications(final Model model) {
        logger.info("nazwa metody = showNotifications");
        try {
            final CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final User user = currentUser.getUser();
            this.httpSession.setAttribute("Notifications", notificationService.findTop5ByUserIdOrUserRoleOrderByDateDesc(user.getId(), user.getRole()));
            final long number = notificationService.countByUnreadAndUserId(true, user.getId());
            this.httpSession.setAttribute("MsgCount", number);
            model.addAttribute("Notifications", notificationService.findByUserIdOrUserRoleOrderByDateDesc(user.getId(), user.getRole()));
            logger.info(notificationService.findByUserIdOrUserRoleOrderByDateDesc(user.getId(), user.getRole()) + "");
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "messages";
    }

    @RequestMapping(value = "/messages/{id}", method = RequestMethod.GET)
    public String readNotification(@PathVariable final long id, Model model) {
        logger.info("nazwa metody = readNotification");
        try {
            Notification notif = notificationService.getNotificationById(id);
            notif.setUnread(false);
            notificationService.createNotification(notif);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "redirect:/messages";
    }
}