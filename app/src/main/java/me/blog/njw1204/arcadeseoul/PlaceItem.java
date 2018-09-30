package me.blog.njw1204.arcadeseoul;

import java.util.ArrayList;

public class PlaceItem {
    private int id;
    private String name;
    private String location;
    private String openTime;
    private ArrayList<String> tags;
    private float rating;
    private int voteCount;
    private String url;

    public PlaceItem(int id, String name, String location, String openTime, ArrayList<String> tags, float rating, int voteCount, String url) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.openTime = openTime;
        this.tags = tags;
        this.rating = rating;
        this.voteCount = voteCount;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}