package com.steegler.tmdb_java.model;

import java.io.Serializable;

/**
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

    public static Movie build(String backdropPath,
                              String originalTitle,
                              int id,
                              int voteCount,
                              String popularity, String posterPath, String releaseDate, String title,
                              String overview,
                              int voteAverage) {
        Movie movie = new Movie();
        movie.backdropPath = backdropPath;
        movie.originalTitle = originalTitle;
        movie.id = id;
        movie.voteCount = voteCount;
        movie.popularity = popularity;
        movie.posterPath = posterPath;
        movie.releaseDate = releaseDate;
        movie.title = title;
        movie.overview = overview;
        movie.voteAverage = voteAverage;
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
}
