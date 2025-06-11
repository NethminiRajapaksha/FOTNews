package com.example.fotnews;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private String title, author, time, body, category;
    private int imageResId;

    public NewsItem(String title, String author, String time, int imageResId, String body, String category) {
        this.title = title;
        this.author = author;
        this.time = time;
        this.imageResId = imageResId;
        this.body = body;
        this.category = category;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getTime() { return time; }
    public int getImageResId() { return imageResId; }
    public String getBody() { return body; }
    public String getCategory() { return category; }
}
