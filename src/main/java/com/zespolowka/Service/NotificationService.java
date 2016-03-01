package com.zespolowka.Service;

import com.zespolowka.Entity.Notification;
import com.zespolowka.Entity.Role;

import java.util.Collection;

/**
 * Created by Peps on 2016-02-27.
 */
public interface NotificationService {

    Collection<Notification> getNotificationsByGroup(Role group);

    Collection<Notification> getNotificationsByUid(long uid);

    Collection<Notification> getAllNotifications();

}


