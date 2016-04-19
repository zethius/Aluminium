package com.zespolowka.service;

import com.zespolowka.entity.user.CurrentUser;
import com.zespolowka.entity.user.Role;
import com.zespolowka.service.inteface.CurrentUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Pitek on 2015-12-12.
 */

@Service
public class CurrentUserServiceImpl implements CurrentUserService {
    private static final Logger logger = LoggerFactory.getLogger(CurrentUserDetailsService.class);

    public CurrentUserServiceImpl() {
    }

    @Override
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        logger.info("Sprawdzam czy uzytkownik = {} ma dostep do informacji o uzytkowniku o id = {}", currentUser.getUsername(), userId);
        return (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.SUPERADMIN || currentUser.getId().equals(userId));
    }

    @Override
    public boolean isVerifiedUser(CurrentUser currentUser) {
        logger.info("Sprawdzam czy uzytkownik = {} zweryfikowal swoje konto za pomoca linku weryfikacyjnego", currentUser.getUsername());
        return (currentUser.getUser().isEnabled());
    }
}
