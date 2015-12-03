package com.zespolowka.controller;

import com.zespolowka.Entity.User;
import com.zespolowka.Entity.UserCreateForm;
import com.zespolowka.Entity.validators.UserCreateValidator;
import com.zespolowka.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Pitek on 2015-11-30.
 */
@Controller
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserCreateValidator userCreateValidator;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        logger.info("nazwa metody = registerPage");
        try {
            model.addAttribute("userCreateForm", new UserCreateForm());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(model.toString());
        }
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerSubmit(@ModelAttribute @Valid UserCreateForm userCreateForm, BindingResult result) {
        logger.info("nazwa metody = registerSubmit");
        if (result.hasErrors()) {
            return "register";
        } else {
            userCreateValidator.validate(userCreateForm, result);
            if (result.hasErrors()) {
                return "register";
            } else {
                User user = userService.create(userCreateForm);
                return "redirect:/user/" + user.getId();
            }
        }

    }

}
