package com.steegler.tmdb_java.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;

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

    private final int cacheSize = ((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8;

    LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getByteCount() / 1024;
        }
    };

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
