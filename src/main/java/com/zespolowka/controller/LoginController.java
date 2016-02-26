package com.zespolowka.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by Pitek on 2015-12-10.
 */

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


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
        model.addAttribute("errorMessage",request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
        return "login";
    }
}
