package com.zespolowka.service.inteface;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.NewMessageForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


public interface NotificationService {
    Notification getNotificationById(Long id);

    Collection<Notification> findTop5ByUserIdOrUserRoleOrderByDateDesc(Long userId, Role userRole);

    Long countByUnreadAndUserId(boolean unread, Long userId);

    Long countByUnreadAndUserRole(boolean unread, Role userRole);

    Notification createNotification(Notification notification);


    Page<Notification> findAllPageable(Pageable pageable, Long userId, Role role);

    @Transactional
    @Modifying
    Notification changeStatus(Long id);

    void sendMessage(NewMessageForm form);

    @Transactional
    @Modifying
    void deleteMessagesByUserId(Long id);

    void deleteMessagesByUserRole(Role userRole);

    void deleteMessagesBySender(User user);

}


