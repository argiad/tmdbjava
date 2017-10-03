package com.steegler.tmdb_java.model;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steegler.tmdb_java.R;
import com.steegler.tmdb_java.utli.LoadImage;
import com.steegler.tmdb_java.view.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Movie list RecyclerView adapter
 * <p>
 * Created by argi on 10/1/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private static final String IMAGE_SIZE = "w90";

    private List<Movie> movieList = new ArrayList<>();

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

        Resources res = holder.ivMoviePoster.getResources();

        Movie movie = movieList.get(position);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvRelease.setText(String.format(res.getString(R.string.item_movie_release), movie.getReleaseDate()));
        holder.tvPopularity.setText(String.format(res.getString(R.string.item_movie_popularity), movie.getPopularity()));
        holder.tvVotes.setText(String.format(res.getString(R.string.item_movie_votes), movie.getVoteCount()));
        holder.ivMoviePoster.setImageBitmap(null);

        // Request image for movie
        new LoadImage(holder.ivMoviePoster).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, IMAGE_SIZE.concat(movie.getPosterPath()));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public Movie getItemOnPosition(int position) {
        if (movieList != null && movieList.size() > position)
            return movieList.get(position);
        return null;
    }

    public void updateData(List<Movie> movieList, boolean isNewData) {

        if (isNewData && movieList != null) {   // need to setup new data for list
            this.movieList.clear();
            this.movieList.addAll(movieList);
        } else if (movieList != null) {         // add data to existed data set
            this.movieList.addAll(movieList);
        } else {                                // clear data set
            this.movieList.clear();
        }
        notifyDataSetChanged();
    }
}
