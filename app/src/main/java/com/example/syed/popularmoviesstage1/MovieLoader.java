package com.example.syed.popularmoviesstage1;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.syed.popularmoviesstage1.Utils.JSONUtils;
import com.example.syed.popularmoviesstage1.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by syed on 2017-06-27.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    public MovieLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        List<Movie> movieList = null;
        Uri uri;
        Movie movie;
        ArrayList<Movie> updatedMovies = new ArrayList<>();

        URL url = NetworkUtils.createUrl(MainActivity.SORT_SELECTED);
        // URL url= NetworkUtils.buildUrl(MainActivity.POPULAR_SORT);
        try {
            String JSONResponseString = NetworkUtils.getResponseFromHttpUrl(url);

            movieList = JSONUtils.parseJSONString(JSONResponseString);

            for (int i = 0; i < movieList.size(); i++) {
                movie = movieList.get(i);
                URL imgURL = NetworkUtils.buildImageURL(movie.getPosterPath());
                Bitmap bitmap = NetworkUtils.getImageFromURL(imgURL);
                //  String img = NetworkUtils.getResponseFromHttpUrl(imgURL);

                //   Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
                movie.setImgBitmap(bitmap);

                updatedMovies.add(movie);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return updatedMovies;
    }
}
