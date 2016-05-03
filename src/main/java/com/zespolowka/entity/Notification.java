package com.zespolowka.entity;

import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "NotificationTable")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 10000)
    private String message;

    private String topic;
    private Date date;
    private boolean unread;

    @OneToOne(targetEntity = User.class, orphanRemoval = false)
    private User sender;
    private long userId;
    @Column
    @Enumerated(EnumType.STRING)
    private Role userRole;

    public Notification() {
    }

    //======DO /SENDMESSAGE==========
    public Notification(String message, String topic, long userId, User sender) { //data auto + nadawca
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        String now = sdf.format(new Date());
        try {
            this.date = sdf.parse(now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.message = message;
        this.topic = topic;
        this.userId = userId;
        this.unread = true;
        this.userRole = null;
        this.sender = sender;
    }

    public Notification(String message, String topic, Role userRole, User sender) { //data auto dla grup + nadawca
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        String now = sdf.format(new Date());
        try {
            this.date = sdf.parse(now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.message = message;
        this.topic = topic;
        this.userRole = userRole;
        this.unread = true;
        this.userId = -1L;
        this.sender = sender;
    }
    //======KONIEC SENDMESSAGE======

    //======DO ZAPELNIENIA BAZY=====
    public Notification(String message, String topic, Date date, long userId, User sender) { //z data + nadawca
        this.message = message;
        this.topic = topic;
        this.date = date;
        this.userId = userId;
        this.unread = true;
        this.userRole = null;
        this.sender = sender;
    }


    public Notification(String message, String topic, Date date, Role userRole, User sender) { //grupowa z data + nadawca
        this.message = message;
        this.topic = topic;
        this.date = date;
        this.userRole = userRole;
        this.unread = true;
        this.userId = -1L;
        this.sender = sender;
    }

    //====================
    public Notification(Notification notification) {

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", topic='" + topic + '\'' +
                ", date=" + date +
                ", unread=" + unread +
                ", userId=" + userId +
                ", userRole=" + userRole +
                '}';
    }
}