package com.zespolowka.validators;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.NewMessageForm;
import com.zespolowka.service.inteface.UserService;
import org.h2.util.New;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by Peps on 2016-04-14.
 */
@Component
public class SendMessageValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(SendMessageValidator.class);

    private final UserService userService;

    @Autowired
    public SendMessageValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(NewMessageForm.class);
    }

    public void validate(Object target, Errors errors) {
        NewMessageForm form = (NewMessageForm) target;
        logger.info("Walidacja adresatow {}", target);
        if (form.getReceivers().isEmpty()) {
            errors.rejectValue("receivers", "notification.receivers_empty");
        }else {
            String result[] = form.getReceivers().split(",");
            for (String s : result) {
                String st = s.replaceAll("\\s+", "");
                if (s.contains("@")) {
                    Optional<User> usr = userService.getUserByEmail(st);
                    if (!usr.isPresent()) {
                        errors.rejectValue("receivers", "notification.receiver_invalid");
                    }
                } else {
                    String st2 = st.toUpperCase();
                    if (!st2.equals(Role.ADMIN.name()) || !st2.equals(Role.SUPERADMIN.name()) || !st2.equals(Role.USER.name())) {
                        errors.rejectValue("receivers", "notification.role_invalid");
                    }
                }
            }
        }
    }
}

