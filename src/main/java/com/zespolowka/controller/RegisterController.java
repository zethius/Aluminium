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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private SendMailService sendMailService;

    public RegisterController() {
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        logger.info("nazwa metody = registerPage");
        try {
            model.addAttribute("userCreateForm", new UserCreateForm());
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            logger.info(model.toString());
        }
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerSubmit(@ModelAttribute @Valid UserCreateForm userCreateForm, BindingResult result, WebRequest request, Model model, HttpServletRequest servletRequest) {
        logger.info("nazwa metody = registerSubmit");
        userCreateValidator.validate(userCreateForm, result);
        if (result.hasErrors()) {
            logger.info(userCreateForm.toString());
            model.addAttribute("userCreateForm", userCreateForm);
            return "register";
        } else {
            User user = userService.create(userCreateForm);
            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = verificationTokenService.create(user, token);
            String url = servletRequest.getRequestURL().toString() + "/registrationConfirm?token=" + verificationToken.getToken();
            sendMailService.sendVerificationMail(url, user);
            logger.info(user.toString());
            model.addAttribute("userCreateForm", new UserCreateForm());
            model.addAttribute("confirmRegistration", true);
            return "register";
        }
    }


    @RequestMapping(value = "/register/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(Model model, @RequestParam("token") String token) {
        logger.info("Potwierdzenie rejestacji");
        Optional<VerificationToken> verificationToken = verificationTokenService.getVerificationTokenByToken(token);
        if (!verificationToken.isPresent()) {
            model.addAttribute("blednyToken", true);
            return "login";
        }
        try {
            User user = verificationToken.get().getUser();
            LocalDateTime localDateTime = LocalDateTime.now();
            long diff = Duration.between(localDateTime, verificationToken.get().getExpiryDate()).toMinutes();

            if (diff < 0L) {
                logger.info(String.format("Token juz jest nieaktulany \n dataDO= %s \n", verificationToken.get().getExpiryDate()));
                model.addAttribute("nieaktualny", true);
                return "login";
            } else {
                logger.info("Token jest aktualny - aktywacja konta");
                user.setEnabled(true);
                userService.update(user);
                model.addAttribute("aktualny", true);
                verificationTokenService.deleteVerificationTokenByUser(user);
                return "login";
            }
        } catch (Exception e) {
            logger.info(token.toString());
            logger.info(e.getMessage(), e);
            logger.info(verificationToken.toString());

        }
        return "login";
    }

    @Override
    public String toString() {
        return "RegisterController{" +
                "userService=" + userService +
                ", userCreateValidator=" + userCreateValidator +
                ", verificationTokenService=" + verificationTokenService +
                ", sendMailService=" + sendMailService +
                '}';
    }
}
