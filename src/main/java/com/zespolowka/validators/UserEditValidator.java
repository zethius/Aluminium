package com.zespolowka.validators;

import com.zespolowka.entity.user.CurrentUser;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.UserEditForm;
import com.zespolowka.service.inteface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by kukol on 2016-04-26.
 */
@Component
public class UserEditValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateValidator.class);

    private final UserService userService;

    @Autowired
    public UserEditValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserEditForm.class);
    }

    public void validate(Object target, Errors errors) {
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = currentUser.getUser();
        UserEditForm form = (UserEditForm) target;
        logger.info("Walidacja edycji{}", target);

        if (form.getPassword() == null || form.getConfirmPassword() == null)
            errors.rejectValue("password", "password_error");
        else if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("password", "password_error");
        }
        if (!user.getEmail().equals(form.getEmail())) {
            if (userService.getUserByEmail(form.getEmail()).isPresent()) {
                errors.rejectValue("email", "email_error");
            }
        }
        if (!user.getId().equals(form.getId())) {
            errors.rejectValue("id", "id_error");
        }
        if (!form.getRole().equals(user.getRole())){
            if (!user.getRole().equals(Role.SUPERADMIN)) {
                errors.rejectValue("role", "role_error");
            }
        }
    }

    @Override
    public String toString() {
        return "UserEditValidator{" +
                "userService=" + userService +
                '}';
    }
}

