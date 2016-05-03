package com.zespolowka.forms;

import com.zespolowka.entity.user.User;

public class NewMessageForm {

    private String message;
    private String topic;
    private String receivers;
    private User sender;

    public NewMessageForm() {
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    @Override
    public String toString() {
        return "NewMessageForm{" +
                "message='" + message + '\'' +
                ", topic='" + topic + '\'' +
                ", receivers='" + receivers + '\'' +
                '}';
    }
}
