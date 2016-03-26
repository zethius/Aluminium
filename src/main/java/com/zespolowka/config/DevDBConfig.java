package com.zespolowka.config;

import com.zespolowka.Entity.Notification;
import com.zespolowka.Entity.Role;
import com.zespolowka.Entity.User;
import com.zespolowka.Service.NotificationService;
import com.zespolowka.repository.NotificationRepository;
import com.zespolowka.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pitek on 2015-12-01.
 */
@Configuration
@Profile("!prod")
public class DevDBConfig {
    private static final Logger logger = LoggerFactory.getLogger(DevDBConfig.class);

    @Autowired
    private UserRepository repository;
    @Autowired
    private NotificationRepository repository2;
    @PostConstruct
    public void populateDatabase() {
        logger.info("ładowanie bazy testowej");
        Date data = new Date();
        Date data2=new Date();
        repository2.save(new Notification("Wiadomosc testowa", data, 1));
        repository2.save(new Notification("Wiad2", data, 1));
        repository2.save(new Notification("GRUPOWA", data, Role.USER));

        //user 2
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        try {
            data2 = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        repository2.save(new Notification("Wiadomosc1", data2, 2));
        repository2.save(new Notification("Wiadomosc2", data, 2));
        repository2.save(new Notification("Wiadomosc3", data2, 2));
        repository2.save(new Notification("Wiadomosc4", data, 2));
        repository2.save(new Notification("Wiadomosc5", data2, 2));
        repository2.save(new Notification("Wiadomosc6", data, 2));
        repository2.save(new Notification("Wiadomosc7", data2, 2));

        repository.save(new User("Imie1", "Nazwisko1", "aaa1@o2.pl", new BCryptPasswordEncoder().encode("aaa")));
        User user = new User("Admin", "admin", "aaa2@o2.pl", new BCryptPasswordEncoder().encode("1"));
        user.setEnabled(true);
        user.setRole(Role.ADMIN);
        repository.save(user);
        user = new User("SuperAdmin", "superadmin", "aaa3@o2.pl", new BCryptPasswordEncoder().encode("a"));
        user.setRole(Role.SUPERADMIN);
        user.setEnabled(true);
        repository.save(user);
    }

}
