package com.ai.game.instashop.Model;

public class PostModel {
    int profile, post, save;
    String name, bio, like, comment, share;

    public PostModel(int profile, int post, int save, String name, String bio, String like, String comment, String share) {
        this.profile = profile;
        this.post = post;
        this.save = save;
        this.name = name;
        this.bio = bio;
        this.like = like;
        this.comment = comment;
        this.share = share;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public void setSave(int save) {
        this.save = save;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public int getProfile() {
        return profile;
    }

    public int getPost() {
        return post;
    }

    public int getSave() {
        return save;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getLike() {
        return like;
    }

    public String getComment() {
        return comment;
    }

    public String getShare() {
        return share;
    }
}
