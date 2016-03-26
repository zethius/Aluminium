package com.zespolowka.repository;

import com.zespolowka.Entity.Notification;
import com.zespolowka.Entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;


/**
 * Created by Peps on 2016-02-27.
 */
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    List<Notification> findByUserIdOrUserRoleOrderByDateDesc(Long userId, Role userRole);

    List<Notification> findTop5ByUserIdOrUserRoleOrderByDateDesc(Long userId,Role userRole);

    Long countByUnreadAndUserId(boolean unread, Long userId);
}

