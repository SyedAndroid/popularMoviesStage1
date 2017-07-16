package com.example.syed.popularmoviesstage1;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.example.syed.popularmoviesstage1.Data.MovieContract;
import com.example.syed.popularmoviesstage1.Utils.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements RecycleViewAdapter.MovieClickListener, LoaderManager.LoaderCallbacks<List<Movie>> {

    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/";
    public static final String IMG_SIZE = "w342";
    public static final String POPULAR_SORT = "popular";
    public static final String TOP_SORT = "top_rated";
    public static final String NOWPLAYING_SORT = "now_playing";
    public static final String UPCOMING_SORT = "upcoming";
    public static final String FAVORITE = "favorite";
    public static String SORT_SELECTED = POPULAR_SORT;
    static List<Movie> movies = new ArrayList<>();
    RecyclerView recycleView;
    RecycleViewAdapter rvAdapter;
    ProgressBar bar;

    public static final Movie getMovie(int position) {
        return movies.get(position);
    }

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
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

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
            setTitle("Popular Movies");
            recycleView.setAdapter(null);

            bar.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(1, null, this);
            SORT_SELECTED = POPULAR_SORT;


        } else if (id == R.id.type2) {
            bar.setVisibility(View.VISIBLE);
            setTitle("Top Rated");
            recycleView.setAdapter(null);
            getLoaderManager().restartLoader(1, null, this);
            SORT_SELECTED = TOP_SORT;

        } else if (id == R.id.type4) {
            bar.setVisibility(View.VISIBLE);
            setTitle("Now Playing");
            recycleView.setAdapter(null);
            getLoaderManager().restartLoader(1, null, this);
            SORT_SELECTED = NOWPLAYING_SORT;
        } else if (id == R.id.type5) {
            bar.setVisibility(View.VISIBLE);
            setTitle("Upcoming Movies");
            recycleView.setAdapter(null);
            getLoaderManager().restartLoader(1, null, this);
            SORT_SELECTED = UPCOMING_SORT;
        } else {
            bar.setVisibility(View.VISIBLE);
            setTitle("Favorites");
            recycleView.setAdapter(null);

            Toast.makeText(this, "GEtting movies marked as fav", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("content://" + MovieContract.CONTENT_AUTHORITY + MovieContract.PATH_MOVIE);
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            int columnIMGIndex = cursor.getColumnIndex(MovieContract.movieEntry.movie_picture);
            int columnID = cursor.getColumnIndex(MovieContract.movieEntry.movie_id);
            cursor.moveToFirst();
            List<Movie> favList = new ArrayList<>();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                byte[] img = cursor.getBlob(columnIMGIndex);
                Bitmap bitmap = BitmapUtil.getImage(img);
                Movie movie = new Movie(bitmap);
                favList.add(movie);
            }
            if (favList.size() > 0) {
                rvAdapter = new RecycleViewAdapter(this, favList);
                SORT_SELECTED = FAVORITE;
                recycleView.setAdapter(rvAdapter);
                bar.setVisibility(View.INVISIBLE);
            } else {
                bar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "You dont have any favorites yet!!", Toast.LENGTH_LONG).show();
            }
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void movieClickLister(int position) {


        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
