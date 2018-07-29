package com.example.booktracker.Networking;

/**
 * Created by Hp on 1/8/2018.
 */

public class Book {
    private String title;
    private String author;
    private int coverImgUrl;
    private int numberOfPage;
    private String OLID;

    public Book() {

    }

    public Book(String t, String a, int i) {
        this.title = t;
        this.author = a;
        this.coverImgUrl = i;
    }

    public Book(String t, String a, int i, String o) {
        this.title = t;
        this.author = a;
        this.coverImgUrl = i;
        this.OLID = o;
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

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public String getOLID() {
        return OLID;
    }

    public void setOLID(String OLID) {
        this.OLID = OLID;
    }
}
