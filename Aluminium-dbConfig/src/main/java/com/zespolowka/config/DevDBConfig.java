package com.zespolowka.config;

import com.zespolowka.Entity.Notification;
import com.zespolowka.Entity.Role;
import com.zespolowka.Entity.User;
import com.zespolowka.repository.NotificationRepository;
import com.zespolowka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by Pitek on 2015-12-01.
 */
@Configuration
@Profile("!prod")
public class DevDBConfig {
    @Autowired
    private UserRepository repository;
    @Autowired
    private NotificationRepository repository2;

    @PostConstruct
    public void populateDatabase() {
        Date data = new Date();
        repository2.save(new Notification("Wiadomosc testowa", data.toString(), 1));
        repository2.save(new Notification("Wiad2", data.toString(), 1));

        repository2.save(new Notification("Wiadomosc testowa2", data.toString(), 2));
        repository2.save(new Notification("Wiadomosc3", data.toString(), 2));
        repository2.save(new Notification("GRUPOWA", data.toString(), Role.USER));

        repository.save(new User("Imie1", "Nazwisko1", "aaa1@o2.pl", new BCryptPasswordEncoder().encode("aaa")));
        User user = new User("Admin", "admin", "aaa2@o2.pl", new BCryptPasswordEncoder().encode("1"));
        user.setRole(Role.ADMIN);
        repository.save(user);
        user = new User("SuperAdmin", "superadmin", "aaa3@o2.pl", new BCryptPasswordEncoder().encode("a"));
        user.setRole(Role.SUPERADMIN);
        repository.save(user);
    }

}
