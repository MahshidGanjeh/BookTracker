package com.example.booktracker.Networking;

/**
 * Created by Hp on 1/8/2018.
 */

public class Book {
    private String title;
    private String author;
    private int coverImgUrl;

    public Book() {

    }

    public Book(String t, String a, int i) {
        this.title = t;
        this.author = a;
        this.coverImgUrl = i;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getCoverImgUrl() {
        return this.coverImgUrl;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public void setAuthor(String a) {
        this.author = a;
    }

    public void setCoverImgUrl(int i) {
        this.coverImgUrl = i;
    }

}
