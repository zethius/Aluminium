package com.zespolowka.controller;

import com.zespolowka.Entity.CurrentUser;
import com.zespolowka.Entity.Notification;
import com.zespolowka.Entity.User;
import com.zespolowka.repository.NotificationRepository;
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

/**
 * Created by Peps on 2016-03-24.
 */
@Controller
//@RequestMapping(value = "/messages")
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private HttpSession httpSession;
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String showNotifications(final Model model) {
        logger.info("nazwa metody = showNotifications");
        try {
            final CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final User user = currentUser.getUser();
            model.addAttribute("Notifications",notificationRepository.findByUserIdOrUserRoleOrderByDateDesc(user.getId(), user.getRole()));
            this.httpSession.setAttribute("Notifications", notificationRepository.findTop5ByUserIdOrUserRoleOrderByDateDesc(user.getId(), user.getRole()));

            final long number= notificationRepository.countByUnreadAndUserId(true,user.getId());
            this.httpSession.setAttribute("MsgCount", number);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "messages";
    }

    @RequestMapping(value = "/messages/{id}", method= RequestMethod.GET)
    public String readNotification(@PathVariable final long id){
        logger.info("nazwa metody = readNotification");
        try {
          Notification notif=  notificationRepository.findOne(id);
            notif.setUnread(false);
            notificationRepository.save(notif);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return "redirect:/messages";
    }
}
