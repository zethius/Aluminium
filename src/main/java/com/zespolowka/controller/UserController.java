package com.zespolowka.controller;

import com.zespolowka.Entity.CurrentUser;
import com.zespolowka.Entity.User;
import com.zespolowka.Entity.UserEditForm;
import com.zespolowka.Service.UserService;
import com.zespolowka.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.NoSuchElementException;

/**
 * Created by Pitek on 2015-12-01.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showUserDetail(@PathVariable Long id, Model model) {
        logger.info("nazwa metody = showUserDetail");
        try {
            User user = userService.getUserById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("Uzytkownik o id =%s nie istnieje", id)));
            model.addAttribute(user);
            model.addAttribute("Notifications", notificationRepository.findByUserIdOrUserRole(id, user.getRole()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(id.toString() + "\n" + model);
        }
        return "userDetail";
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String showCurrentUserDetail(Model model) {
        logger.info("nazwa metody = showCurrentUserDetail");
        try {
            CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user=currentUser.getUser();
            model.addAttribute(user);
            model.addAttribute("Notifications", notificationRepository.findByUserIdOrUserRole(user.getId(), user.getRole()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "userDetail";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editCurrentUserDetail(Model model) {
        logger.info("nazwa metody = showCurrentUserDetail");
        try {
            CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user=currentUser.getUser();
            model.addAttribute("userEditForm", new UserEditForm(user));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "userEdit";
    }

    //na razie nie zapisuje
    @RequestMapping(value = "/edit/", method = RequestMethod.POST)
    public String saveCurrentUser(@ModelAttribute @Valid UserEditForm userEditForm, Errors errors) {
        logger.info("nazwa metody = saveCurrentUser");
        if (errors.hasErrors()) {
            return "userEdit";
        } else {
            userService.editUser(userEditForm);
            return "redirect:/user/show";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable Integer id, Model model, SecurityContextHolderAwareRequestWrapper request) {
        logger.debug("nazwa metody = editUser");
        try {
            model.addAttribute("userEditForm", new UserEditForm(userService.getUserById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("Uzytkownik o id =%s nie istnieje", id)))));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(id.toString() + "\n" + model + "\n" + userService.getUserById(id));
        }
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String saveUser(@PathVariable Integer id, @ModelAttribute @Valid UserEditForm userEditForm, Errors errors) {
        logger.info("nazwa metody = saveUser");
        if (errors.hasErrors()) {
            return "userEdit";
        } else {
            User user = userService.editUser(userEditForm);
            return "redirect:/user/" + user.getId();
        }
    }

}

