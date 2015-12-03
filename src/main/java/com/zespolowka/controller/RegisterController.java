package com.zespolowka.controller;

import com.zespolowka.Entity.User;
import com.zespolowka.Entity.UserCreateForm;
import com.zespolowka.Entity.validators.UserCreateValidator;
import com.zespolowka.Service.UserService;
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
    @Autowired
    private UserService userService;

    @Autowired
    private UserCreateValidator userCreateValidator;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        model.addAttribute("userCreateForm", new UserCreateForm());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerSubmit(@ModelAttribute @Valid UserCreateForm userCreateForm, BindingResult result) {
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
