package com.zespolowka.service.inteface;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.Role;

import java.util.Collection;

/**
 * Created by Peps on 2016-02-27.
 */
public interface NotificationService {

    Collection<Notification> getNotificationsByGroup(Role group);

    Collection<Notification> getNotificationsByUid(long uid);

    Collection<Notification> getAllNotifications();

}


