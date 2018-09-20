package me.blog.njw1204.arcadeseoul;

import java.util.ArrayList;

public class PlaceItem {
    private String name;
    private String location;
    private String openTime;
    private ArrayList<String> tags;
    private float rating;
    private int voteCount;

    public PlaceItem(String name, String location, String openTime, ArrayList<String> tags, float rating, int voteCount) {
        this.name = name;
        this.location = location;
        this.openTime = openTime;
        this.tags = tags;
        this.rating = rating;
        this.voteCount = voteCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}