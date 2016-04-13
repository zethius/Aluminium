package com.zespolowka.repository;


import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrUserRoleOrderByDateDesc(Long userId, Role userRole);

    List<Notification> findTop5ByUserIdOrUserRoleOrderByDateDesc(Long userId, Role userRole);

    Long countByUnreadAndUserId(boolean unread, Long userId);

    Long countByUnreadAndUserRole(boolean unread, Role userRole);

   // Long countByUnreadAndUserRoleOrUserId(boolean unread, Role userRole, Long userId);

    Page<Notification> findAllByUserIdOrUserRoleOrderByDateDesc(Pageable var1,Long userId, Role userRole);
}

