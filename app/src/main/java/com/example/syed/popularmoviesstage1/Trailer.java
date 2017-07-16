package com.example.syed.popularmoviesstage1;

import java.util.ArrayList;

/**
 * Created by syed on 2017-07-13.
 */

public class Trailer {
    private int movieID;
    private String name;
    private String trailerLinks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrailerLinks() {
        return trailerLinks;
    }

    public void setTrailerLinks(String trailerLinks) {
        this.trailerLinks = trailerLinks;
    }

    public int getMovie_id() {
        return movieID;
    }

    public void setMovie_id(int movie_id) {
        this.movieID = movie_id;
    }

    public Trailer(String name, String trailerLinks, int movie_id) {
        this.movieID=movie_id;
        this.name = name;
        this.trailerLinks = trailerLinks;
    }

    public Trailer(String name, String trailerLinks) {
        this.name = name;
        this.trailerLinks = trailerLinks;
    }
}
