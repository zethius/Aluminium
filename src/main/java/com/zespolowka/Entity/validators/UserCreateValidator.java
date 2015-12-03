package com.zespolowka.Entity.validators;

import com.zespolowka.Entity.UserCreateForm;
import com.zespolowka.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Admin on 2015-12-03.
 */
@Component
public class UserCreateValidator implements Validator {

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
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("password", "", "Password do not match");
        }

        if (userService.getUserByEmail(form.getEmail()) != null) {
            errors.rejectValue("email", "", "User with this email already exists");
        }
    }
}
