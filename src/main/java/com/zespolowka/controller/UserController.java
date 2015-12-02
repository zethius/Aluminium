package com.zespolowka.controller;

import com.zespolowka.Entity.User;
import com.zespolowka.Entity.UserEditForm;
import com.zespolowka.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Pitek on 2015-12-01.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showUserDetail(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute(user);
        return "userDetail";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable Integer id, Model model) {
        model.addAttribute("userEditForm", new UserEditForm(userService.getUserById(id)));
        return "userEdit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String saveUser(@PathVariable Integer id, @ModelAttribute @Valid UserEditForm userEditForm, Errors errors) {
        if (errors.hasErrors()) {
            return "userEdit";
        } else {
            User user = userService.editUser(userEditForm);
            return "redirect:/user/" + user.getId();
        }
    }

}

