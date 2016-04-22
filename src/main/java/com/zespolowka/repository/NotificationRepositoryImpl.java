package com.zespolowka.repository;

import com.zespolowka.entity.user.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class NotificationRepositoryImpl implements CustomNotificationRepository {

    @PersistenceContext
    private EntityManager em;

<<<<<<< HEAD
    public NotificationRepositoryImpl() {
    }

=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e

    @Override
    public Long messageCount(long userId, Role role) {
        String sql = "select count(n.id) from Notification n where (n.userId=:userId) or (n.userRole=:role)";
        Query query = em.createQuery(sql);
        query.setParameter("userId", userId);
        query.setParameter("role", role);
        return (Long) query.getSingleResult();
    }
<<<<<<< HEAD

    @Override
    public String toString() {
        return "NotificationRepositoryImpl{" +
                "em=" + em +
                '}';
    }
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
}
