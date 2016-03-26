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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showUserDetail(@PathVariable final Long id, final Model model) {
        logger.info("nazwa metody = showUserDetail");
        try {
            final User user = userService.getUserById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("Uzytkownik o id =%s nie istnieje", id)));
            model.addAttribute(user);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(id.toString() + "\n" + model);
        }
        return "userDetail";
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String showCurrentUserDetail(final Model model) {
        logger.info("nazwa metody = showCurrentUserDetail");
        try {
            final CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            logger.info(currentUser.toString());
            final User user = currentUser.getUser();
            logger.info(user.toString());
            model.addAttribute(user);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "userDetail";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editCurrentUserDetail(final Model model) {
        logger.info("nazwa metody = editCurrentUserDetail");
        try {
            final CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final User user = currentUser.getUser();
            model.addAttribute("userEditForm", new UserEditForm(user));
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "userEdit";
    }

    //zapisuje ale nie wyswleta na /user/show
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveCurrentUser(@ModelAttribute @Valid final UserEditForm userEditForm, final Errors errors) {
        logger.info("nazwa metody = saveCurrentUser");
        if (errors.hasErrors()) {
            return "userEdit";
        } else {
            final User user = userService.editUser(userEditForm);
            CurrentUser currentUser=new CurrentUser(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), currentUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info(user.toString());
            return "redirect:/user/show";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable final Integer id, final Model model, final SecurityContextHolderAwareRequestWrapper request) {
        logger.debug("nazwa metody = editUser");
        try {
            model.addAttribute("userEditForm", new UserEditForm(userService.getUserById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("Uzytkownik o id =%s nie istnieje", id)))));
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(id.toString() + "\n" + model + "\n" + userService.getUserById(id));
        }
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String saveUser(@PathVariable final Integer id, @ModelAttribute @Valid final UserEditForm userEditForm, final Errors errors) {
        logger.info("nazwa metody = saveUser");
        if (errors.hasErrors()) {
            return "userEdit";
        } else {
            final User user = userService.editUser(userEditForm);
            return "redirect:/user/" + user.getId();
        }

    }

}

