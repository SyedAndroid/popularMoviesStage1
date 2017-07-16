package com.example.syed.popularmoviesstage1;

import android.graphics.Bitmap;

/**
 * Created by syed on 2017-06-26.
 */

public class Movie {
    Bitmap bitmap;
    private int id;
    private String title;
    private String posterPath;
    private String synopsis;
    private double ratings;
    private String releaseDate;

    public Movie(int id, String title, String posterpath, String synopsis, double ratings, String releaseDate) {
        this.id = id;
        this.title = title;
        this.posterPath = posterpath;
        this.synopsis = synopsis;
        this.ratings = ratings;
        this.releaseDate = releaseDate;
    }

    public Movie(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getRatings() {
        return ratings;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Bitmap getImgBitmap() {
        return bitmap;
    }

    public void setImgBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
