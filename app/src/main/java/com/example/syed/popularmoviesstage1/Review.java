package com.example.syed.popularmoviesstage1;

import java.net.URL;

/**
 * Created by syed on 2017-07-16.
 */

public class Review {
    private String author;
    private String content;
    private String url;

    public Review(String author, String content, String url) {
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
