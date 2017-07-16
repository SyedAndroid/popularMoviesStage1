package com.example.syed.popularmoviesstage1;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syed.popularmoviesstage1.Data.MovieContract;
import com.example.syed.popularmoviesstage1.Utils.BitmapUtil;
import com.example.syed.popularmoviesstage1.Utils.JSONUtils;
import com.example.syed.popularmoviesstage1.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Trailer>> {

    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setContentView(R.layout.activity_details);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.activity_detail_landscape);
                break;
        }

        TextView title = (TextView) findViewById(R.id.title);
        TextView ratings = (TextView) findViewById(R.id.ratings);
        ImageView imagePoster = (ImageView) findViewById(R.id.detail_img);
        TextView release = (TextView) findViewById(R.id.release_date);
        TextView synopsis = (TextView) findViewById(R.id.synopsis);
        final Button button = (Button) findViewById(R.id.button_fav);
        Button reviewsButton = (Button) findViewById(R.id.button_reviews);

        Intent intent = getIntent();
        if(intent.hasExtra("position") && intent != null) {

            Bundle bundle = intent.getExtras();
            int position = bundle.getInt("position");


            if (MainActivity.SORT_SELECTED == MainActivity.FAVORITE) {
                Uri uri = Uri.parse("content://" + MovieContract.CONTENT_AUTHORITY + MovieContract.PATH_MOVIE + "/" + position);

                String[] args = new String[]{(position + 1) + ""};
                Cursor cursor = getContentResolver().query(uri, null, null, args, null);
                int titleColumnIndex = cursor.getColumnIndex(MovieContract.movieEntry.movie_title);
                int dateColumnIndex = cursor.getColumnIndex(MovieContract.movieEntry.movie_date);
                int movieIDColumnIndex = cursor.getColumnIndex(MovieContract.movieEntry.movie_id);
                int synopisColumnIndex = cursor.getColumnIndex(MovieContract.movieEntry.movie_synopsis);
                int ratingsColumnIndex = cursor.getColumnIndex(MovieContract.movieEntry.movie_ratings);
                int pictureColumnIndex = cursor.getColumnIndex(MovieContract.movieEntry.movie_picture);


                cursor.moveToFirst();
                int movieID = cursor.getInt(movieIDColumnIndex);
                title.setText(cursor.getString(titleColumnIndex));
                ratings.setText(cursor.getString(ratingsColumnIndex));
                release.setText(cursor.getString(dateColumnIndex));
                imagePoster.setImageBitmap(BitmapUtil.getImage(cursor.getBlob(pictureColumnIndex)));
                synopsis.setText(cursor.getString(synopisColumnIndex));

                Uri trailerUri = Uri.parse("content://" + MovieContract.CONTENT_AUTHORITY + MovieContract.PATH_TRAILER + "/" + movieID);
                String[] args1 = new String[]{movieID + ""};

                Cursor cursor1 = getContentResolver().query(trailerUri, null, null, args1, null);
                int columnIDTrailer = cursor1.getColumnIndex(MovieContract.trailerEntry.trailer_movie_id);
                int nameTrailer = cursor1.getColumnIndex(MovieContract.trailerEntry.trailer_name);
                int linkIDTrailer = cursor1.getColumnIndex(MovieContract.trailerEntry.trailer_link);

                ArrayList<Trailer> strings = new ArrayList<>();
                cursor1.moveToFirst();
                for (int i = 0; i < cursor1.getCount(); i++) {
                    cursor1.moveToPosition(i);
                    int movie_id = cursor1.getInt(columnIDTrailer);
                    String trailerName = cursor1.getString(nameTrailer);
                    String link = cursor1.getString(linkIDTrailer);

                    Trailer trailer = new Trailer(trailerName, link, movie_id);
                    strings.add(trailer);
                }
                CustomAdapter trailerAdapter = new CustomAdapter(this, strings);
                ListView listView = (ListView) findViewById(R.id.list_view);
                listView.setAdapter(trailerAdapter);

                button.setVisibility(View.INVISIBLE);
                reviewsButton.setVisibility(View.INVISIBLE);

            } else {
                if (MainActivity.SORT_SELECTED == MainActivity.UPCOMING_SORT) {
                    reviewsButton.setVisibility(View.INVISIBLE);
                }

                movie = MainActivity.getMovie(position);
                title.setText(movie.getTitle());
                ratings.setText(String.valueOf(movie.getRatings()));
                imagePoster.setImageBitmap(movie.getImgBitmap());
                release.setText(movie.getReleaseDate());
                synopsis.setText(movie.getSynopsis());

                Uri uri = Uri.parse("content://" + MovieContract.CONTENT_AUTHORITY + MovieContract.PATH_MOVIE);
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);

                int columnID = cursor.getColumnIndex(MovieContract.movieEntry.movie_id);
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    int movie_id = cursor.getInt(columnID);
                    if (movie_id == movie.getId()) {
                        button.setEnabled(false);
                        button.setText("Marked as Favorite");
                        button.setBackgroundColor(Color.GRAY);
                    }

                }

                startLoader();

                reviewsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intentReview = new Intent(getBaseContext(), ReviewActivity.class);
                        intentReview.putExtra("movieID", movie.getId());
                        startActivity(intentReview);

                    }
                });

            }
        } else
            Toast.makeText(this, "No data passed!!", Toast.LENGTH_LONG).show();

    }

    public void startLoader() {
        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(2, null, this);
    }

    @Override
    public Loader<ArrayList<Trailer>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<ArrayList<Trailer>>(this) {

            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public ArrayList<Trailer> loadInBackground() {

                String JSONResponseVideos = "";
                String JSONResponseReviews = "";
                int movieID = movie.getId();
                ArrayList<Trailer> trailerList = new ArrayList<>();
                URL url = NetworkUtils.createUrl(movieID + "/videos");

                ArrayList<Review> reviewsList = new ArrayList<>();
                URL reviewUrl = NetworkUtils.createUrl(movieID + "/reviews");

                try {

                    JSONResponseVideos = NetworkUtils.getResponseFromHttpUrl(url);
                    trailerList = JSONUtils.parseTrailerJSONString(JSONResponseVideos);

                    JSONResponseReviews = NetworkUtils.getResponseFromHttpUrl(reviewUrl);
                    reviewsList = JSONUtils.parseReviewJSONString(JSONResponseReviews);

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return trailerList;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Trailer>> loader, final ArrayList<Trailer> strings) {


        CustomAdapter trailerAdapter = new CustomAdapter(this, strings);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(trailerAdapter);

        final Button button = (Button) findViewById(R.id.button_fav);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Button clicked", Toast.LENGTH_LONG).show();
                byte[] bytes = BitmapUtil.getBytes(movie.getImgBitmap());
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContract.movieEntry.movie_title, movie.getTitle());
                contentValues.put(MovieContract.movieEntry.movie_date, movie.getReleaseDate());
                contentValues.put(MovieContract.movieEntry.movie_id, movie.getId());
                contentValues.put(MovieContract.movieEntry.movie_synopsis, movie.getSynopsis());
                contentValues.put(MovieContract.movieEntry.movie_picture, bytes);
                contentValues.put(MovieContract.movieEntry.movie_ratings, movie.getRatings());

                Uri uri = Uri.parse("content://" + MovieContract.CONTENT_AUTHORITY + MovieContract.PATH_MOVIE);
                Uri returnedUri = getContentResolver().insert(uri, contentValues);


                for (int i = 0; i < strings.size(); i++) {
                    ContentValues trailerValues = new ContentValues();

                    Trailer trailer = strings.get(i);
                    trailerValues.put(MovieContract.trailerEntry.trailer_movie_id, movie.getId());
                    trailerValues.put(MovieContract.trailerEntry.trailer_name, trailer.getName());
                    trailerValues.put(MovieContract.trailerEntry.trailer_link, trailer.getTrailerLinks());

                    Uri trailerUri = Uri.parse("content://" + MovieContract.CONTENT_AUTHORITY + MovieContract.PATH_TRAILER + "/" + movie.getId());

                    Uri rowsInserted = getContentResolver().insert(trailerUri, trailerValues);
                    if (rowsInserted != null) {
                        Toast.makeText(getBaseContext(), trailerUri.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                button.setText("Marked as Favorite");
                button.setBackgroundColor(Color.GRAY);
                button.setEnabled(false);
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Trailer>> loader) {
        loader.abandon();
    }



}
