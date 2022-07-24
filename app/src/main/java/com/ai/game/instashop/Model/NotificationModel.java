package com.ai.game.instashop.Model;

public class NotificationModel {
    private String notificationById;
    private String notificationType;
    private String notificationId;
    private long notificationAtTime;
    private String postId, postedBy;
    private boolean notificationOpened;

    public NotificationModel(String notificationById, String notificationType, long notificationAtTime, String postId, String postedBy, boolean notificationOpened) {
        this.notificationById = notificationById;
        this.notificationType = notificationType;
        this.notificationAtTime = notificationAtTime;
        this.postId = postId;
        this.postedBy = postedBy;
        this.notificationOpened = notificationOpened;
    }

    public NotificationModel() {
    }

    public String getNotificationById() {
        return notificationById;
    }

    public void setNotificationById(String notificationById) {
        this.notificationById = notificationById;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public long getNotificationAtTime() {
        return notificationAtTime;
    }

    public void setNotificationAtTime(long notificationAtTime) {
        this.notificationAtTime = notificationAtTime;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public boolean isNotificationOpened() {
        return notificationOpened;
    }

    public void setNotificationOpened(boolean notificationOpened) {
        this.notificationOpened = notificationOpened;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
}
