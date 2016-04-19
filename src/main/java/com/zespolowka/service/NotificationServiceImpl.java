package com.zespolowka.service;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.NewMessageForm;
import com.zespolowka.repository.NotificationRepository;
import com.zespolowka.repository.UserRepository;
import com.zespolowka.service.inteface.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Notification getNotificationById(Long id) {
        logger.info("getNotificationById = {}", id);
        return notificationRepository.findOne(id);
    }

    @Override
    public Collection<Notification> getAllNotifications() {
        logger.info("getAllNotifications = ");
        return notificationRepository.findAll();
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
        logger.info("createNotif" + notification);
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotifications(Integer page, Integer size) {
        String DEFAULT_SORT_BY_DATE = "date";
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, DEFAULT_SORT_BY_DATE);
        return notificationRepository.findAll(pageRequest).getContent().stream().map(Notification::new).collect(Collectors.toList());
    }

    public Page<Notification> findAllPageable(Pageable pageable, Long userId, Role userRol) {
        return notificationRepository.findAllByUserIdOrUserRoleOrderByDateDesc(pageable, userId, userRol);
    }

    @Override
    public Notification changeStatus(Long idNotification) {
        Notification notification = notificationRepository.getOne(idNotification);
        notification.setUnread(false);
        return notificationRepository.save(notification);
    }

    public void sendMessage(NewMessageForm form) {
        String receivers;
        if (form.getReceivers().endsWith(", "))
            receivers = form.getReceivers().substring(0, form.getReceivers().length() - 2);
        else receivers = form.getReceivers();

        String result[] = receivers.split(",");
        Notification notif;
        ArrayList<String> wyslane = new ArrayList<>();
        for (String s : result) {

            String st = s.replaceAll("\\s+", "");
            if (wyslane.contains(st)) {
                continue;
            }
            if (st.contains("@")) {
                User usr = userRepository.findUserByEmail(st)
                        .orElseThrow(() -> new NoSuchElementException(String.format("Uzytkownik o emailu =%s nie istnieje", st)));
                notif = new Notification(form.getMessage(), form.getTopic(), new Date(), usr.getId());
                logger.info("Wiadomosc wyslana do: " + st);
                notificationRepository.save(notif);
                wyslane.add(st);
            } else {
                String st2 = st.toUpperCase();
                logger.info(st2);
                logger.info("role:" + Role.valueOf(st.toUpperCase()).name());
                if (st2.equals(Role.ADMIN.name()) || st2.equals(Role.SUPERADMIN.name()) || st2.equals(Role.USER.name())) {
                    notif = new Notification(form.getMessage(), form.getTopic(), new Date(), Role.valueOf(st2));
                    logger.info("Grupowa wiadomosc do: " + st);
                    notificationRepository.save(notif);
                    wyslane.add(st);
                }
            }
        }
    }

    public void deleteMessagesByUserId(Long id) {
        logger.info("deleteMessagesByUserId");
        logger.info("Przed usunieciem:" + notificationRepository.count());
        notificationRepository.deleteByUserId(id);
        logger.info("Po usunieciu:" + notificationRepository.count());
    }

    @Override
    public String toString() {
        return "NotificationServiceImpl{" +
                "notificationRepository=" + notificationRepository +
                ", userRepository=" + userRepository +
                '}';
    }
}



