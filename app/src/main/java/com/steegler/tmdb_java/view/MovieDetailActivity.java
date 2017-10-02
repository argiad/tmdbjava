package com.steegler.tmdb_java.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.steegler.tmdb_java.utli.LoadImage;
import com.steegler.tmdb_java.model.Movie;
import com.steegler.tmdb_java.R;

/**
 * Created by argi on 10/1/17.
 */

public class MovieDetailActivity extends AppCompatActivity {

    Movie movie;
    ImageView ivMoviePoster;
    TextView tvTitle, tvRelease, tvPopularity, tvVotes, tvOverview, tvVoteAverage;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("movie"))
            movie = (Movie) getIntent().getSerializableExtra("movie");

        setContentView(R.layout.detail);

        ivMoviePoster =  findViewById(R.id.ivMoviePoster);

        tvTitle = findViewById(R.id.tvTitle);
        tvRelease = findViewById(R.id.tvRelease);
        tvPopularity = findViewById(R.id.tvPopularity);
        tvVotes = findViewById(R.id.tvVotes);
        tvOverview = findViewById(R.id.tvOverview);
        tvVoteAverage = findViewById(R.id.tvVoteAverage);


        tvTitle.setText(movie.getTitle());
        tvRelease.setText(String.format("Release at: %s",movie.getReleaseDate()));
        tvPopularity.setText(String.format("Popularity %s",movie.getPopularity()));
        tvVotes.setText(String.format("Votes: %d",movie.getVoteCount()));
        tvOverview.setText(movie.getOverview());
        tvVoteAverage.setText(String.format("Average: %d",movie.getVoteAverage()));

        new LoadImage(ivMoviePoster).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"w150".concat(movie.getPosterPath()));


    }
}
