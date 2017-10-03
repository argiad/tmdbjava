package com.steegler.tmdb_java.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Movie respond data model
 *
 * Created by argi on 10/1/17.
 */

public class Movie implements Serializable {
    private String backdropPath;
    private String originalTitle;
    private int id;
    private int voteCount;
    private String popularity;
    private String posterPath;
    private String releaseDate;
    private String title;
    private String overview;
    private int voteAverage;
    private boolean hasVideo;

    public static Movie build(JSONObject jsonMovieObject) throws JSONException {
        Movie movie = new Movie();
        movie.backdropPath = jsonMovieObject.getString("backdrop_path");
        movie.originalTitle = jsonMovieObject.has("original_title") ? jsonMovieObject.getString("original_title") : jsonMovieObject.optString("original_name");
        movie.id = Integer.parseInt(jsonMovieObject.getString("id"));
        movie.voteCount = jsonMovieObject.getInt("vote_count");
        movie.popularity = jsonMovieObject.getString("popularity");
        movie.posterPath = jsonMovieObject.getString("poster_path");
        movie.releaseDate = jsonMovieObject.has("release_date") ? jsonMovieObject.getString("release_date") : jsonMovieObject.optString("first_air_date");
        movie.title = jsonMovieObject.has("title") ? jsonMovieObject.getString("title") : jsonMovieObject.optString("original_name");
        movie.overview = jsonMovieObject.getString("overview");
        movie.voteAverage = jsonMovieObject.getInt("vote_average");
        movie.hasVideo = jsonMovieObject.has("video") && jsonMovieObject.optBoolean("video");
        return movie;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getOverview() {
        return overview;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }
}
