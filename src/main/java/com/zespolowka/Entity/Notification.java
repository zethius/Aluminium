package com.zespolowka.Entity;

import javax.persistence.*;
import java.util.Date;

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

    private Date date;
    private boolean unread;
    private long userId;
    @Column
    @Enumerated(EnumType.STRING)
    private Role userRole;

    public Notification() {
    }

    public Notification(String message, Date date, Role userRole) {
        this.message = message;
        this.date = date;
        this.userId = -1;
        this.userRole = userRole;
        this.unread=true;
    }

    public Notification(String message, Date data, long userId) {
        this.message = message;
        this.date = data;
        this.userId = userId;
        this.userRole = null;
        this.unread=true;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
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

