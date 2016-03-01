package com.zespolowka.repository;

import com.zespolowka.Entity.Notification;
import com.zespolowka.Entity.Role;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Peps on 2016-02-27.
 */
@Repository
public class InMemoryNotificationRepository {

    private List<Notification> listofNotifications = new ArrayList<>();

    public InMemoryNotificationRepository() {
        Date data = new Date();
        Notification pow1 = new Notification("Wiadomosc testowa", data.toString(), 1);
        Notification pow2 = new Notification("Wiadomosc testowa2", data.toString(), 2);
        Notification pow3 = new Notification("GRUPOWA", data.toString(), Role.USER);
        listofNotifications.add(pow1);
        listofNotifications.add(pow2);
        listofNotifications.add(pow3);
    }

    public List<Notification> getAllNotifications() {
        return listofNotifications;
    }

}
//DO WYJEBANIA
