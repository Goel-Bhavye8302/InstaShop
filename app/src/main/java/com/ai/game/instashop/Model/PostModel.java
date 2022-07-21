package com.ai.game.instashop.Model;

public class PostModel {
    private String postId, postImage, postDescription, postedById;
    private Long postedAtTime;
    private int likeCount, commentCount;

    public PostModel(){
    }

    public PostModel(String postId, String postImage, String postDescription, String postedById, Long postedAtTime) {
        this.postId = postId;
        this.postImage = postImage;
        this.postDescription = postDescription;
        this.postedById = postedById;
        this.postedAtTime = postedAtTime;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostedById() {
        return postedById;
    }

    public void setPostedById(String postedById) {
        this.postedById = postedById;
    }

    public Long getPostedAtTime() {
        return postedAtTime;
    }

    public void setPostedAtTime(Long postedAtTime) {
        this.postedAtTime = postedAtTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
