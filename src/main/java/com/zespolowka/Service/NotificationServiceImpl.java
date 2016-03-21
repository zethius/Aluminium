package com.zespolowka.Service;

import com.zespolowka.Entity.Notification;
import com.zespolowka.Entity.Role;
import com.zespolowka.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Peps on 2016-02-27.
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    // zbedne wszystko
    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Collection<Notification> getNotificationsByGroup(Role group) {
        //pobrac pozycje z notificationRepository i gdy poz.Gropu = group dodac do temp i return temp;
        return (Collection<Notification>) notificationRepository.findAll();
    }

    @Override
    public Collection<Notification> getNotificationsByUid(long uid) {
        logger.info("Pobieranie powiadomien dla uid = {}", uid);

        Collection<Notification> temp = new ArrayList<>();
        // Notification poz=notificationRepository.findOne(uid);
        //  logger.info("Test={}", poz.getMessage());
        // temp.add(poz);
        temp.add(notificationRepository.findOne(uid));//bieda
        return temp;
        // return (Collection<Notification>) notificationRepository.findAll();
    }

    @Override
    public Collection<Notification> getAllNotifications() {
        logger.info("Pobieranie wszystkich powiadomien");
        return (Collection<Notification>) notificationRepository.findAll();
    }


}



