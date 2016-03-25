package com.zespolowka.entity;

import com.zespolowka.entity.user.Role;

import javax.persistence.*;

/**
 * Created by Peps on 2016-02-27.
 */

@Entity
@Table(name = "NotificationTable")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    private String date;

    private long userId;
    @Column
    @Enumerated(EnumType.STRING)
    private Role userRole;

    public Notification() {
    }

    public Notification(String message, String date, Role userRole) {
        this.message = message;
        this.date = date;
        this.userId = -1;
        this.userRole = userRole;
    }

    public Notification(String message, String data, long userId) {
        this.message = message;
        this.date = data;
        this.userId = userId;
        this.userRole = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "group=" + userRole +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                ", id=" + id +
                '}';
    }
}

