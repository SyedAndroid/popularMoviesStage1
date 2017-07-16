package com.example.syed.popularmoviesstage1.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.syed.popularmoviesstage1.Movie;

/**
 * Created by syed on 2017-07-13.
 */



public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="movies.db";

    public static final int version=2;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_DBTABLE_MOVIE="CREATE TABLE "+ MovieContract.movieEntry.MOVIE_TABBLE_NAME+"("
                                                +MovieContract.movieEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                +MovieContract.movieEntry.movie_id+" INTEGER NOT NULL, "
                                                +MovieContract.movieEntry.movie_title+" TEXT NOT NULL,"
                                                + MovieContract.movieEntry.movie_date+" TEXT NOT NULL,"
                                                + MovieContract.movieEntry.movie_ratings+" TEXT NOT NULL,"
                                                + MovieContract.movieEntry.movie_synopsis+" TEXT NOT NULL,"
                                                + MovieContract.movieEntry.movie_picture +" BLOB NOT NULL);" ;

        sqLiteDatabase.execSQL(CREATE_DBTABLE_MOVIE);

        final String CREATE_DBTABLE_TRAILER=" CREATE TABLE "+MovieContract.trailerEntry.TABLE_NAME_TRAILER+"("
                                            + MovieContract.trailerEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                                            + MovieContract.trailerEntry.trailer_movie_id+" INTEGER NOT NULL,"
                                            +MovieContract.trailerEntry.trailer_name+" TEXT NOT NULL,"
                                            +MovieContract.trailerEntry.trailer_link+" TEXT NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_DBTABLE_TRAILER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.movieEntry.MOVIE_TABBLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.movieEntry.MOVIE_TABBLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
