package com.zespolowka.controller;


import com.zespolowka.Entity.CurrentUser;
import com.zespolowka.Entity.User;
import com.zespolowka.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Pitek on 2015-12-10.
 */

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private HttpSession httpSession;
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(Model model, @RequestParam Optional<String> error) {
        logger.info("nazwa metody = getLoginPage");
        model.addAttribute("error", error);
        //powiadomienia
        logger.info("test:"+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
       try {
           final CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           final User user = currentUser.getUser();
           this.httpSession.setAttribute("Notifications", notificationRepository.findTop5ByUserIdOrUserRoleOrderByDateDesc(user.getId(), user.getRole()));
       }catch(ClassCastException ex){
           logger.info(ex.getMessage());
       }
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model, HttpServletRequest request) {
        logger.info("nazwa metody = loginError");
        model.addAttribute("loginError", true);
        model.addAttribute("errorMessage",request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
        return "login";
    }
    @RequestMapping(value = "/remindPassword", method = RequestMethod.GET)
    public String getRemindPasswordPage() {
        logger.info("nazwa metody = getRemindPasswordPage");
        return "remindPassword";
    }
}
