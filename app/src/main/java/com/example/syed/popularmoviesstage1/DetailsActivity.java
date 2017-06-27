package com.example.syed.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView title = (TextView) findViewById(R.id.title);
        TextView ratings = (TextView) findViewById(R.id.ratings);
        ImageView imagePoster = (ImageView) findViewById(R.id.detail_img);
        TextView release = (TextView) findViewById(R.id.release_date);
        TextView synopsis = (TextView) findViewById(R.id.synopsis);

        Intent intent = getIntent();
        if(intent.hasExtra("position") && intent != null) {

            Bundle bundle = intent.getExtras();
            int position = bundle.getInt("position");

            Movie movie = MainActivity.getMovie(position);

            title.setText(movie.getTitle());
            ratings.setText(String.valueOf(movie.getRatings()));
            imagePoster.setImageBitmap(movie.getImgBitmap());
            release.setText(movie.getReleaseDate());
            synopsis.setText(movie.getSynopsis());

        } else
            Toast.makeText(this,"No data passed!!",Toast.LENGTH_LONG);

    }

}
