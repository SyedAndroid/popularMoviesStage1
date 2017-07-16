package com.example.syed.popularmoviesstage1.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.Contacts;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by syed on 2017-07-13.
 */

public class MovieProvider extends ContentProvider {

    MovieDbHelper dbHelper;

    public static final int PATH_MOVIE = 100;
    public static final int PATH_MOVIE_WITH_ID=101;
    public static final int PATH_TRAILERS_WITH_ID=102;

    private static final UriMatcher matcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){

        final String authority = MovieContract.CONTENT_AUTHORITY;

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(authority,MovieContract.PATH_MOVIE,PATH_MOVIE);
        matcher.addURI(authority,MovieContract.PATH_MOVIE+"/#",PATH_MOVIE_WITH_ID);
        matcher.addURI(authority,MovieContract.PATH_TRAILER+"/#",PATH_TRAILERS_WITH_ID);



        return matcher;
    }


    @Override
    public boolean onCreate() {
        Context context=getContext();
        dbHelper= new MovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] selectionARGS, @Nullable String s1) {

        int match = matcher.match(uri);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor;
        switch (match){
            case PATH_MOVIE:
                cursor= db.query(MovieContract.movieEntry.MOVIE_TABBLE_NAME,null,null,null,null,null,null);
                break;

            case PATH_MOVIE_WITH_ID:
                cursor =db.query(MovieContract.movieEntry.MOVIE_TABBLE_NAME,null,MovieContract.movieEntry._ID+"=?",selectionARGS,null,null,null);
                break;
            case PATH_TRAILERS_WITH_ID:
                cursor =db.query(MovieContract.trailerEntry.TABLE_NAME_TRAILER,null,MovieContract.trailerEntry.trailer_movie_id+"=?",selectionARGS,null,null,null);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);


        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {


        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        int match = matcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;
        long row_inserted;
        switch (match) {
            case PATH_MOVIE:
                row_inserted = db.insert(MovieContract.movieEntry.MOVIE_TABBLE_NAME, null, contentValues);
                if ( row_inserted > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieContract.movieEntry.CONTENT_URI, row_inserted);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case PATH_TRAILERS_WITH_ID:
                row_inserted = db.insert(MovieContract.trailerEntry.TABLE_NAME_TRAILER, null, contentValues);
                if ( row_inserted > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieContract.trailerEntry.CONTENT_URI, row_inserted);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {


        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
