package com.steegler.tmdb_java.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.steegler.tmdb_java.interfaces.MovieCallBack;
import com.steegler.tmdb_java.model.Movie;
import com.steegler.tmdb_java.model.Video;

import java.util.List;

/**
 * Abstract Base Activity with implemented MovieCallback
 *
 * Created by argi on 10/2/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements MovieCallBack {

    protected static final String EXTRA_MOVIE = "movie";

    String TAG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
    }


    @Override
    public void updateData(List<Movie> movieList, boolean isNewData, int totalResults) {
    }

    @Override
    public void updateData(Object video) {
    }

}
