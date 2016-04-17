package com.zespolowka.controller;

import com.zespolowka.entity.VerificationToken;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.UserCreateForm;
import com.zespolowka.service.inteface.SendMailService;
import com.zespolowka.service.inteface.UserService;
import com.zespolowka.service.inteface.VerificationTokenService;
import com.zespolowka.validators.UserCreateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Created by Pitek on 2015-11-30.
 */
@Controller
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    /**
     * TODO
     * Zamienic na wstrzykiwanie przez konstruktor
     */

    @Autowired
    private UserService userService;

    @Autowired
    private UserCreateValidator userCreateValidator;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private SendMailService sendMailService;


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
    public String registerSubmit(@ModelAttribute @Valid UserCreateForm userCreateForm, BindingResult result, WebRequest request, Model model) {
        logger.info("nazwa metody = registerSubmit");
        userCreateValidator.validate(userCreateForm, result);
        if (result.hasErrors()) {
            return "register";
        } else {
            User user = userService.create(userCreateForm);
            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = verificationTokenService.create(user, token);
            String url = "http://localhost:8080" + request.getContextPath() + "/registrationConfirm?token=" + token;
            sendMailService.sendVerificationMail(url, user);
            logger.info(user.toString());
            model.addAttribute("userCreateForm", new UserCreateForm());
            model.addAttribute("confirmRegistration", true);
            return "register";
        }
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(Model model, @RequestParam("token") String token) {
        logger.info("Potwierdzenie rejestacji");
        VerificationToken verificationToken = verificationTokenService.getVerificationTokenByToken(token).
                orElseThrow(() -> new NoSuchElementException(String.format("Podany token = %s nie istnieje", token)));
        User user = verificationToken.getUser();
        LocalDateTime localDateTime = LocalDateTime.now();
        long diff = Duration.between(localDateTime, verificationToken.getExpiryDate()).toMinutes();

        /**
         * TODO
         * Poprawic to
         */
        if (diff < 0) {
            logger.info(String.format("Token juz jest nieaktulany \n dataDO= %s \n", verificationToken.getExpiryDate()));
            return "login";
        } else {
            logger.info("Token jest aktualny - aktywacja konta");
            user.setEnabled(true);
            userService.update(user);
            return "login";
        }
    }

}
