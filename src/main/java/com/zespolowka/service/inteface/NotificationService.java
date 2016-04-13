package com.zespolowka.service.inteface;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.Role;
import com.zespolowka.forms.NewMessageForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;


public interface NotificationService {
    Notification getNotificationById(Long id);

    Collection<Notification> getAllNotifications();

    Collection<Notification> findByUserIdOrUserRoleOrderByDateDesc(Long userId, Role userRole);

    Collection<Notification> findTop5ByUserIdOrUserRoleOrderByDateDesc(Long userId, Role userRole);

    Long countByUnreadAndUserId(boolean unread, Long userId);

    Long countByUnreadAndUserRole(boolean unread, Role userRole);

    Notification createNotification(Notification notification);

    List<Notification> getProducts(final Integer page, final Integer size);

    Page<Notification> findAllPageable(Pageable pageable,Long userId,Role role);

    Notification changeStatus(Long id);

    void send(NewMessageForm form);

}


