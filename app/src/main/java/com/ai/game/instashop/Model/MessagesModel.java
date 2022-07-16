package com.ai.game.instashop.Model;

public class MessagesModel {
    String uid, message;
    Long timeStamp;

    public MessagesModel(String uid, String message, Long timeStamp) {
        this.uid = uid;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public MessagesModel(String uid, String message) {
        this.uid = uid;
        this.message = message;
    }

    public MessagesModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
