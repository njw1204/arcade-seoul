package me.blog.njw1204.arcadeseoul;

public class ReviewItem {
    private String contents;
    private String date;
    private String author;
    private float rating;

    public ReviewItem(String contents, String date, String author, float rating) {
        this.contents = contents;
        this.date = date;
        this.author = author;
        this.rating = rating;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
