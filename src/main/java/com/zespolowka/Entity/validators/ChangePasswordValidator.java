package com.zespolowka.Entity.validators;

import com.zespolowka.Entity.User;
import com.zespolowka.Entity.UserEditForm;
import com.zespolowka.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
 * Created by Peps on 2016-04-03.
 */
@Component
public class ChangePasswordValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(ChangePasswordValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserEditForm.class);
    }

    public void validate(Object target, Errors errors) {
        UserEditForm form = (UserEditForm) target;
        logger.info("Walidacja hasla {}", target);
        if(!form.getPassword().isEmpty()) {
            if (!form.getPassword().equals(form.getConfirmPassword())) {
                errors.rejectValue("password", "password_error");
            }
            if (form.getPassword().length() < 6 || form.getPassword().length() > 25) {
                errors.rejectValue("password", "passwordlength_error");
            }
        }
    }
}
