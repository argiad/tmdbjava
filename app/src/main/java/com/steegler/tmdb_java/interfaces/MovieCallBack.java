package com.steegler.tmdb_java.interfaces;

import com.steegler.tmdb_java.model.Movie;
import com.steegler.tmdb_java.model.Video;

import java.util.List;

/**
 * Callback for implementation TMDB data loading in activity
 * Created by argi on 10/2/17.
 */

public interface MovieCallBack {
    /**
     *
     * @param movieList array of Movie for ListView
     * @param isNewData is adapter need to add or clear previous dataset
     * @param totalResults total result coynter for paging calculation
     */
    void updateData(List<Movie> movieList, boolean isNewData, int totalResults);

    /**
     *
     * @param object Video or Movie object
     */
    void updateData(Object object);
}
