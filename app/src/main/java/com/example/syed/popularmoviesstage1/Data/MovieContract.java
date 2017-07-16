package com.example.syed.popularmoviesstage1.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by syed on 2017-07-13.
 */

public class MovieContract {

    public final static String CONTENT_AUTHORITY="com.example.syed.popularmoviesstage1";

    public final static Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public final static String PATH_MOVIE ="/movie";
    public final static String PATH_TRAILER ="/trailer";


    public static final class movieEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String MOVIE_TABBLE_NAME="movie";

        public  static final String _ID = BaseColumns._ID;

        public static final String movie_id ="movieID";

        public static final String movie_title = "title";

        public static final String movie_ratings= "ratings";

        public static final String movie_date="release";

        public static final String movie_synopsis="synopsis";

        public static final String movie_picture="picture";

    }

    public static final class trailerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public  static final String _ID = BaseColumns._ID;

        public static final String TABLE_NAME_TRAILER="trailer";

        public static final String trailer_name ="name";

        public static final String trailer_link ="link";

        public static final String trailer_movie_id ="movieID";

    }



}
