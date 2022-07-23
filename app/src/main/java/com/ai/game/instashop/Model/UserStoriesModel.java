package com.ai.game.instashop.Model;

public class UserStoriesModel {
    private String story;
    private long storyAtTime;

    public UserStoriesModel(String story, long storyAtTime) {
        this.story = story;
        this.storyAtTime = storyAtTime;
    }

    public UserStoriesModel() {
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public long getStoryAtTime() {
        return storyAtTime;
    }

    public void setStoryAtTime(long storyAtTime) {
        this.storyAtTime = storyAtTime;
    }
}
