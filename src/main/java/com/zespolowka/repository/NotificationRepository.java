package com.zespolowka.repository;


import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findTop5ByUserIdOrUserRoleOrderByDateDesc(Long userId, Role userRole);

    @Transactional
    @Modifying
    List<Notification> deleteByUserId(long UserId);

    Long countByUnreadAndUserId(boolean unread, Long userId);

    Long countByUnreadAndUserRole(boolean unread, Role userRole);

    Page<Notification> findAllByUserIdOrUserRoleOrderByDateDesc(Pageable var1, Long userId, Role userRole);

    @Transactional
    @Modifying
    void deleteMessagesByUserRole(Role userRole);

    @Transactional
    @Modifying
    void deleteMessagesBySender(User user);
}

