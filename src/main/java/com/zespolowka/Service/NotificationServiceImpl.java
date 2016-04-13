package com.zespolowka.service;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.Role;
import com.zespolowka.repository.NotificationRepository;
import com.zespolowka.service.inteface.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    @Override
    public Notification getNotificationById(Long id) {
        logger.info("getNotificationById = {}", id);
        return notificationRepository.findOne(id);
    }

    @Override
    public Collection<Notification> getAllNotifications() {
        logger.info("getAllNotifications = ");
        return (Collection<Notification>) notificationRepository.findAll();
    }

    @Override
    public Collection<Notification> findByUserIdOrUserRoleOrderByDateDesc(Long userId, Role userRole) {
        logger.info("findByUserId={}OrUserRole={}OrderByDateDesc = ", userId, userRole);
        return notificationRepository.findByUserIdOrUserRoleOrderByDateDesc(userId, userRole);
    }

    @Override
    public Collection<Notification> findTop5ByUserIdOrUserRoleOrderByDateDesc(Long userId, Role userRole) {
        logger.info("findTop5ByUserId={}OrUserRole={}OrderByDateDesc = ", userId, userRole);
        return notificationRepository.findTop5ByUserIdOrUserRoleOrderByDateDesc(userId, userRole);
    }

    @Override
    public Long countByUnreadAndUserId(boolean unread, Long userId) {
        logger.info("countByUnread={}AndUserId={}", unread, userId);
        return notificationRepository.countByUnreadAndUserId(unread, userId);
    }

    @Override
    public Long countByUnreadAndUserRole(boolean unread, Role userRole) {
        logger.info("countByUnread={}AndUserId={}", unread, userRole);
        return notificationRepository.countByUnreadAndUserRole(unread, userRole);
    }

    @Override
    public Notification createNotification(Notification notification) {
        logger.info("createNotif"+notification);
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getProducts(Integer page, Integer size) {
        String DEFAULT_SORT_BY_DATE = "date";
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, DEFAULT_SORT_BY_DATE);
        return notificationRepository.findAll(pageRequest).getContent().stream().map(Notification::new).collect(Collectors.toList());
    }

    public Page<Notification> findAllPageable(Pageable pageable,Long userId, Role userRol) {
        return notificationRepository.findAllByUserIdOrUserRoleOrderByDateDesc(pageable,userId,userRol);
    }

    @Override
    public Notification changeStatus(Long idNotification) {
        Notification notification = notificationRepository.getOne(idNotification);
        notification.setUnread(false);
        return notificationRepository.save(notification);
    }
}



