package com.zespolowka.repository;

import com.zespolowka.Entity.Notification;
import com.zespolowka.Entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by Peps on 2016-02-27.
 */
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);

    List<Notification> findByUserRole(Role userRole);

    List<Notification> findByUserIdOrUserRole(Long userId, Role userRole);
}

