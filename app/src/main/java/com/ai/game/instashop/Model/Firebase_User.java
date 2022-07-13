package com.ai.game.instashop.Model;

import java.util.ArrayList;

public class Firebase_User {
    private String name, email, profession, password, uid;
    private String coverPhoto, profilePhoto;
    private int FollowersCount = 0;
    private String lastMessage;

    public Firebase_User(){
    }

    public Firebase_User(String name, String email, String profession, String password) {
        this.name = name;
        this.email = email;
        this.profession = profession;
        this.password = password;
    }

    public Firebase_User(String name, String email, String profession, String password, String lastMessage) {
        this.name = name;
        this.email = email;
        this.profession = profession;
        this.password = password;
        this.lastMessage = lastMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getFollowersCount() {
        return FollowersCount;
    }

    public void setFollowersCount(int followersCount) {
        FollowersCount = followersCount;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
