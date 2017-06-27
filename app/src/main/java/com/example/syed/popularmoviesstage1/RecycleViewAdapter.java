package com.example.syed.popularmoviesstage1;

/**
 * Created by syed on 2017-06-27.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MovieViewHolder> {


    List<Movie> movies;
    private MovieClickListener mMovieClickLister;

    public RecycleViewAdapter(MovieClickListener movieClickListener, List<Movie> movies) {
        this.mMovieClickLister = movieClickListener;
        this.movies = movies;
    }

    public interface MovieClickListener {
        public void movieClickLister(int position);
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForListItem = R.layout.list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutForListItem, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie movie = movies.get(position);
        Bitmap img = movie.getImgBitmap();

        holder.thumbnail.setImageBitmap(img);

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView thumbnail;

        public MovieViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mMovieClickLister.movieClickLister(adapterPosition);
        }
    }
}
