package com.zespolowka.Entity.validators;

import com.zespolowka.Entity.UserCreateForm;
import com.zespolowka.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Admin on 2015-12-03.
 */
@Component
public class UserCreateValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateValidator.class);

    private final UserService userService;

    @Autowired
    public UserCreateValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    public void validate(Object target, Errors errors) {
        UserCreateForm form = (UserCreateForm) target;
        logger.info("Validating {}", target);
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("password", "password_error");
        }

        if (userService.getUserByEmail(form.getEmail()) != null) {
            errors.rejectValue("email", "email_error");
        }
    }
}
