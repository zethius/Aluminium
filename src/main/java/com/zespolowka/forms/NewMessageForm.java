package com.zespolowka.forms;

/**
 * Created by Peps on 2016-04-13.
 */
public class NewMessageForm {

    private String message;
    private String topic;
    private String receivers;

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
}
