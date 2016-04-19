package com.zespolowka.service;

import com.zespolowka.entity.user.CurrentUser;
import com.zespolowka.entity.user.User;
import com.zespolowka.service.inteface.UserService;
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
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
    private final UserService userService;

    @Autowired
    public CurrentUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Autentykacja uzytkownika o mailu = {}", email);
        try {
            User user = userService.getUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("Uzytkownik z mailem=%s nie istnieje", email)));
            return new CurrentUser(user);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "CurrentUserDetailsService{" +
                "userService=" + userService +
                '}';
    }
}