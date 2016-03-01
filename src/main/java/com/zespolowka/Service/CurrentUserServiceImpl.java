package com.zespolowka.Service;

import com.zespolowka.Entity.CurrentUser;
import com.zespolowka.Entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Pitek on 2015-12-12.
 */

@Service
public class CurrentUserServiceImpl implements CurrentUserService {
    private static final Logger logger = LoggerFactory.getLogger(CurrentUserDetailsService.class);

    @Override
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        logger.info("Sprawdzam czy uzytkownik = {} ma dostep do informacji o uzytkowniku o id = {}", currentUser.getUsername(), userId);
        return currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId));
    }
}
