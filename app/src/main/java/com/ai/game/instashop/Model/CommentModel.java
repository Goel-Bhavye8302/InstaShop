package com.ai.game.instashop.Model;

public class CommentModel {
    private Long commentTime;
    private String comment, commentedById;

    public CommentModel(Long commentTime, String comment, String commentedById) {
        this.commentTime = commentTime;
        this.comment = comment;
        this.commentedById = commentedById;
    }

    public CommentModel() {
    }

    public Long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Long commentTime) {
        this.commentTime = commentTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentedById() {
        return commentedById;
    }

    public void setCommentedById(String commentedById) {
        this.commentedById = commentedById;
    }

}
