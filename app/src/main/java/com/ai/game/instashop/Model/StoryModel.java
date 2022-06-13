package com.ai.game.instashop.Model;

// This class is responsible for holding the structure of every item of recycler view

public class StoryModel {
    int story, profile;
    String name;

    public StoryModel(int story, int profile, String name) {
        this.story = story;
        this.profile = profile;
        this.name = name;
    }

    public int getStory() { return story;}

    public int getProfile() { return profile; }

    public String getName() { return name; }

    public void setStory(int story) { this.story = story; }

    public void setProfile(int profile) { this.profile = profile; }

    public void setName(String name) { this.name = name; }
}
