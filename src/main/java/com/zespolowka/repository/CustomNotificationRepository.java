package com.zespolowka.repository;


import com.zespolowka.entity.user.Role;

public interface CustomNotificationRepository {
    Long messageCount(long userId, Role role);
}
