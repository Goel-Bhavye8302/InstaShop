package com.ai.game.instashop.Model;

// This class is responsible for holding the structure of every item of recycler view

import java.util.ArrayList;

public class StoryModel {
    private String storyById;
    private long storyAtTime;
    ArrayList<UserStoriesModel> storyList;

    public StoryModel(String storyById, long storyAtTime, ArrayList<UserStoriesModel> storyList) {
        this.storyById = storyById;
        this.storyAtTime = storyAtTime;
        this.storyList = storyList;
    }

    public StoryModel() {
    }


    public String getStoryById() {
        return storyById;
    }

    public void setStoryById(String storyById) {
        this.storyById = storyById;
    }

    public long getStoryAtTime() {
        return storyAtTime;
    }

    public void setStoryAtTime(long storyAtTime) {
        this.storyAtTime = storyAtTime;
    }

    public ArrayList<UserStoriesModel> getStoryList() {
        return storyList;
    }

    public void setStoryList(ArrayList<UserStoriesModel> storyList) {
        this.storyList = storyList;
    }
}
