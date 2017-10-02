package com.steegler.tmdb_java.model;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steegler.tmdb_java.utli.LoadImage;
import com.steegler.tmdb_java.R;
import com.steegler.tmdb_java.view.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by argi on 10/1/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    List<Movie> movieList = new ArrayList<>();

    public static MovieAdapter getInstance() {
        MovieAdapter adapter = new MovieAdapter();
        return adapter;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvRelease.setText(String.format("Release at: %s",movie.getReleaseDate()));
        holder.tvPopularity.setText(String.format("Popularity %s",movie.getPopularity()));
        holder.tvVotes.setText(String.format("Votes: %d",movie.getVoteCount()));

        holder.ivMoviePoster.setImageBitmap(null);
        new LoadImage(holder.ivMoviePoster).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"w90".concat(movie.getPosterPath()));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public Movie getItemOnPosition(int position){
        if (movieList != null && movieList.size() > position)
            return movieList.get(position);
        return  null;
    }

    public void updateData(List<Movie> movieList, boolean isNewData) {
        if (isNewData && movieList != null) {
            this.movieList.clear();
            this.movieList.addAll(movieList);
        } else if (movieList != null) {
            this.movieList.addAll(movieList);
        } else {
            this.movieList.clear();
        }
        notifyDataSetChanged();
    }
}
