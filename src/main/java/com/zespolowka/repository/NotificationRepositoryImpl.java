package com.zespolowka.repository;

import com.zespolowka.entity.user.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class NotificationRepositoryImpl implements CustomNotificationRepository {

    @PersistenceContext
    private EntityManager em;

    public NotificationRepositoryImpl() {
    }


    @Override
    public Long messageCount(long userId, Role role) {
        String sql = "select count(n.id) from Notification n where (n.userId=:userId) or (n.userRole=:role)";
        Query query = em.createQuery(sql);
        query.setParameter("userId", userId);
        query.setParameter("role", role);
        return (Long) query.getSingleResult();
    }

    @Override
    public String toString() {
        return "NotificationRepositoryImpl{" +
                "em=" + em +
                '}';
    }
}
