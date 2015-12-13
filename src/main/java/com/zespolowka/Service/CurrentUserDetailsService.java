package com.zespolowka.Service;

import com.zespolowka.Entity.CurrentUser;
import com.zespolowka.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Pitek on 2015-12-11.
 */
@Service
public class CurrentUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    public CurrentUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Autentykacja uzytkownika o mailu = {}", email);
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        return new CurrentUser(user);
    }
}