package com.zespolowka.repository;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.Role;
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

