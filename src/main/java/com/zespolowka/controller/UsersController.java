package com.zespolowka.controller;

import com.zespolowka.service.inteface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Admin on 2015-12-01.
 */
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(model.toString() + "\n" + userService.getAllUsers().toString());
        }
        return "users";
    }

}
