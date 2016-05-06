package com.zespolowka.controller;


import com.zespolowka.entity.user.User;
import com.zespolowka.service.inteface.SendMailService;
import com.zespolowka.service.inteface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;
    private final SendMailService sendMailService;

    @Autowired
    public LoginController(UserService userService, SendMailService sendMailService) {
        this.userService = userService;
        this.sendMailService = sendMailService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(Model model, @RequestParam Optional<String> error) {
        logger.info("nazwa metody = getLoginPage");
        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model, HttpServletRequest request) {
        logger.info("nazwa metody = loginError");
        model.addAttribute("loginError", true);
        model.addAttribute("errorMessage", request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
        return "login";
    }

    @RequestMapping(value = "/remindPassword", method = RequestMethod.GET)
    public String getRemindPasswordPage() {
        logger.info("nazwa metody = getRemindPasswordPage");
        return "remindPassword";
    }

    @RequestMapping(value = "/remindPassword", method = RequestMethod.POST)
    public String sendRemindPassword(HttpServletRequest request, Model model) {
        logger.info("nazwa metody = sendRemindPassword");
        String email = request.getParameter("username");
        try {
            User user = userService.getUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("Uzytkownik z mailem=%s nie istnieje", email)));
            sendMailService.sendReminderMail(user);
            logger.info("Email:", email);
            model.addAttribute("sukces", true);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
        return "remindPassword";
    }

    @Override
    public String toString() {
        return "LoginController{" +
                "userService=" + userService +
                ", sendMailService=" + sendMailService +
                '}';
    }
}
