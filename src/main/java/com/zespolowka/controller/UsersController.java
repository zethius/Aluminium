package com.zespolowka.controller;

import com.zespolowka.entity.user.User;
import com.zespolowka.forms.UserEditForm;
import com.zespolowka.service.inteface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping("/users")
    public String getUsersPage(Model model) {
        logger.info("nazwa metody = getUsersPage");
        try {
            model.addAttribute("Users", userService.getAllUsers());

            model.addAttribute("usersEditForm", new UserEditForm(new User()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(model.toString() + "\n" + userService.getAllUsers().toString());
        }
        return "users";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String saveEditedUser(@ModelAttribute @Valid final UserEditForm usersEditForm,
                                 final Errors errors) {

        logger.info("nazwa metody = saveUser");
        if (errors.hasErrors()) {
            logger.info("dupa blada@@@@@@@@@@@");
            return "redirect:/users";
        } else {
            logger.info("duasddddddddddddddpa blada@@@@@@@@@@@2");
            final User user = userService.editUser(usersEditForm);
            return "redirect:/users";
        }

    }

}
