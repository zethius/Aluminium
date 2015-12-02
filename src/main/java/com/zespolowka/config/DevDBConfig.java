package com.zespolowka.config;

import com.zespolowka.Entity.Role;
import com.zespolowka.Entity.User;
import com.zespolowka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

/**
 * Created by Pitek on 2015-12-01.
 */
@Configuration
@Profile("!prod")
public class DevDBConfig {
    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void populateDatabase() {
        repository.save(new User("Imie1", "Nazwisko1", "aaa@o2.pl", "abcd"));
        User user = new User("Admin", "admin", "aaa@o2.pl", "abcd");
        user.setRole(Role.ADMIN);
        repository.save(user);
        user = new User("SuperAdmin", "superadmin", "aaa@o2.pl", "abcd");
        user.setRole(Role.SUPERADMIN);
        repository.save(user);
    }

}
