package com.example.syed.popularmoviesstage1;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements RecycleViewAdapter.MovieClickListener, LoaderManager.LoaderCallbacks<List<Movie>> {

    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/";
    public static final String IMG_SIZE = "w342";
    public static final String POPULAR_SORT = "/movie/popular";
    public static final String TOP_SORT = "/movie/top_rated";
    public static String SORT_SELECTED = POPULAR_SORT;
    RecyclerView recycleView;
    RecycleViewAdapter rvAdapter;
    ProgressBar bar;

    public static final Movie getMovie(int position) {
        return movies.get(position);
    }

    static List<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        bar.setVisibility(View.VISIBLE);
        recycleView = (RecyclerView) findViewById(R.id.recycle_view);

        GridLayoutManager glm = new GridLayoutManager(this, 3);

        recycleView.setLayoutManager(glm);

        startLoader();


    }

    public void startLoader() {
        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(1, null, this);
    }


    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        if (data.size() != 0) {
            rvAdapter = new RecycleViewAdapter(this, data);
            movies = data;
            recycleView.setAdapter(rvAdapter);
            bar.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(this, "Please connect to Internet and try again!!", Toast.LENGTH_LONG).show();
            bar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        loader.abandon();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.type) {
            bar.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(0, null, this);

        } else {
            bar.setVisibility(View.VISIBLE);
            setTitle("Top Rated");
            recycleView.setAdapter(null);
            getLoaderManager().restartLoader(0, null, this);
            SORT_SELECTED = TOP_SORT;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void movieClickLister(int position) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);

    }


}
