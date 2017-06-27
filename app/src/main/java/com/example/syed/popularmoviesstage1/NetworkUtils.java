package com.example.syed.popularmoviesstage1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by syed on 2017-06-26.
 */

public class NetworkUtils {
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String POPULAR_QUERY_PARAM = "/movie/popular";
    private static final String TOP_RATED_QUERY_PARAM = "/movie/top_rated";
    private static final String API_QUERY_PARAM = "?api_key=";
    private static final String API_KEY = "PUT YOUR KEY HERE";
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


    public static URL buildUrl(String type) {
        Uri buildUri;

        if (type.equals("popular")) {
            buildUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(POPULAR_QUERY_PARAM)
                    .appendQueryParameter(API_QUERY_PARAM, API_KEY)
                    .build();

        } else {
            buildUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(TOP_RATED_QUERY_PARAM)
                    .appendQueryParameter(API_QUERY_PARAM, API_KEY)
                    .build();
        }
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static Bitmap getImageFromURL(URL url) {
        Bitmap bmp = null;

        try {
            InputStream in = url.openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            // log error
        }
        return bmp;
    }


    public static URL buildImageURL(String string) {


        String uriString = MainActivity.BASE_IMG_URL + MainActivity.IMG_SIZE + string;
        URL url = null;
        try {
            url = new URL(uriString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL createUrl(String sort) {
        String stringUrl = BASE_URL + sort + API_QUERY_PARAM + API_KEY;
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }


}
