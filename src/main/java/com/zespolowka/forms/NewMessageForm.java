package com.zespolowka.forms;

public class NewMessageForm {

    private String message;
    private String topic;
    private String receivers;

<<<<<<< HEAD
    public NewMessageForm() {
    }

=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
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
