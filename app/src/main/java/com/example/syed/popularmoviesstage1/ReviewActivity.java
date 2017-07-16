package com.example.syed.popularmoviesstage1;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.syed.popularmoviesstage1.Utils.JSONUtils;
import com.example.syed.popularmoviesstage1.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Review>>{
    int movieID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        movieID= intent.getIntExtra("movieID",0);

        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(3, null, this);


    }

    @Override
    public Loader<ArrayList<Review>> onCreateLoader(int id, Bundle args) {


        return new AsyncTaskLoader<ArrayList<Review>>(this) {


            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public ArrayList<Review> loadInBackground() {


                String JSONResponseReviews="";
                ArrayList<Review> reviewsList = new ArrayList<>();
                URL reviewUrl = NetworkUtils.createUrl(movieID+"/reviews");
                try {
                JSONResponseReviews= NetworkUtils.getResponseFromHttpUrl(reviewUrl);
                reviewsList = JSONUtils.parseReviewJSONString(JSONResponseReviews);

            } catch (IOException e) {
                e.printStackTrace();
            }


                return reviewsList;
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> data) {

        ListView reviewList= (ListView) findViewById(R.id.listview_review);
        ReviewAdapter reviewAdapter= new ReviewAdapter(this,data);
        reviewList.setAdapter(reviewAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Review>> loader) {

    }
}
