package com.ai.game.instashop.Model;

public class ProfileFriendsModel {
    String followedById;
    long followedAtTime;

    public ProfileFriendsModel() {
    }

    public String getFollowedById() {
        return followedById;
    }

    public void setFollowedById(String followedById) {
        this.followedById = followedById;
    }

    public long getFollowedAtTime() {
        return followedAtTime;
    }

    public void setFollowedAtTime(long followedAt) {
        this.followedAtTime = followedAt;
    }
}
