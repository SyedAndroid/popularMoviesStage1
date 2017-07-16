package com.example.syed.popularmoviesstage1.Utils;

import com.example.syed.popularmoviesstage1.Movie;
import com.example.syed.popularmoviesstage1.Review;
import com.example.syed.popularmoviesstage1.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by syed on 2017-06-26.
 */

public class JSONUtils {

    public static ArrayList<Movie> parseJSONString(String JSONString) {

        ArrayList<Movie> resultList = new ArrayList<>();

        try {
            JSONObject baseJSON = new JSONObject(JSONString);
            JSONArray resultArray = baseJSON.getJSONArray("results");

            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject movieJSON = resultArray.getJSONObject(i);
                int id = movieJSON.getInt("id");
                String title = movieJSON.getString("title");
                String posterpath = movieJSON.getString("poster_path");
                String synopsis = movieJSON.getString("overview");
                double ratings = movieJSON.getDouble("vote_average");
                String releaseDate = movieJSON.getString("release_date");

                resultList.add(new Movie(id, title, posterpath, synopsis, ratings, releaseDate));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return resultList;

    }

    public static ArrayList<Trailer> parseTrailerJSONString(String JSONString) {

        ArrayList<Trailer> trailerList = new ArrayList<>();
        JSONObject baseJSON = null;
        try {
            baseJSON = new JSONObject(JSONString);
            JSONArray resultArray = baseJSON.getJSONArray("results");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject jsonObject = resultArray.getJSONObject(i);
                String trailerKey = jsonObject.getString("key");
                String trailerName = jsonObject.getString("name");
                trailerList.add(new Trailer(trailerName, trailerKey));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailerList;
    }


    public static ArrayList<Review> parseReviewJSONString(String JSONString) {

        ArrayList<Review> reviewList = new ArrayList<>();
        JSONObject baseJSON = null;
        try {
            baseJSON = new JSONObject(JSONString);
            JSONArray resultArray = baseJSON.getJSONArray("results");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject jsonObject = resultArray.getJSONObject(i);
                String author = jsonObject.getString("author");
                String content = jsonObject.getString("content");
                String urlString = jsonObject.getString("url");
                reviewList.add(new Review(author, content, urlString));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewList;
    }

}
