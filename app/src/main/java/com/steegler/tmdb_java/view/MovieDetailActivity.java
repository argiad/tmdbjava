package com.steegler.tmdb_java.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.steegler.tmdb_java.R;
import com.steegler.tmdb_java.model.Movie;
import com.steegler.tmdb_java.model.Video;
import com.steegler.tmdb_java.utli.LoadImage;
import com.steegler.tmdb_java.utli.RetrieveMovieDetailTask;

/**
 * Created by argi on 10/1/17.
 */

public class MovieDetailActivity extends BaseActivity {

    private static final String IMAGE_SIZE = "w150";
    private static final String YT_SERVICE = "YouTube";

    Movie movie;
    ImageView ivMoviePoster;
    TextView tvTitle, tvRelease, tvPopularity, tvVotes, tvOverview, tvVoteAverage;
    WebView webView;
    Video video;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(EXTRA_MOVIE))
            movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        if (savedInstanceState != null) {
            movie = (Movie) savedInstanceState.getSerializable(EXTRA_MOVIE);
        }

        setContentView(R.layout.detail);

        ivMoviePoster = findViewById(R.id.ivMoviePoster);

        tvTitle = findViewById(R.id.tvTitle);
        tvRelease = findViewById(R.id.tvRelease);
        tvPopularity = findViewById(R.id.tvPopularity);
        tvVotes = findViewById(R.id.tvVotes);
        tvOverview = findViewById(R.id.tvOverview);
        tvVoteAverage = findViewById(R.id.tvVoteAverage);
        webView = findViewById(R.id.webView);


        tvTitle.setText(movie.getTitle());
        tvRelease.setText(String.format(getString(R.string.item_movie_release), movie.getReleaseDate()));
        tvPopularity.setText(String.format(getString(R.string.item_movie_popularity), movie.getPopularity()));
        tvVotes.setText(String.format(getString(R.string.item_movie_votes), movie.getVoteCount()));
        tvOverview.setText(movie.getOverview());
        tvVoteAverage.setText(String.format(getString(R.string.movie_average), movie.getVoteAverage()));

        new LoadImage(ivMoviePoster).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, IMAGE_SIZE.concat(movie.getPosterPath()));

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUserAgentString(getString(R.string.user_agent));

//        if (movie.isHasVideo())
        requestVideo(movie.getId());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_MOVIE, movie);
    }

    @Override
    public void updateData(Object object) {

        // hide WebView in case  no video or not YT
        if (!((Video) object).getSite().equalsIgnoreCase(YT_SERVICE)) {
            webView.setVisibility(View.GONE);
            return;
        }

        // show video on WebView
        this.video = (Video) object;
        loadVideo(video.getKey());
    }

    @Override
    public void onBackPressed() {
        webView.loadData("<body></body>", "text/html", "utf-8");
        super.onBackPressed();
    }

    /**
     * Load video into Webvie frame
     * @param key YuTube(service) video key use Video.getKey
     */
    private void loadVideo(String key) {
        String frameVideo = "<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/" + key + "\" frameborder=\"0\">\n</iframe>";
        Log.d(TAG, frameVideo);
        webView.loadData(frameVideo, "text/html", "utf-8");
        webView.setVisibility(View.VISIBLE);
    }

    /**
     * Request video array list from TMDB using Async Task
     * @param id movie ID use Movie.getId
     */
    private void requestVideo(int id) {
        RetrieveMovieDetailTask retrieveTask = new RetrieveMovieDetailTask();
        retrieveTask.setCallBack(this);
        retrieveTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
    }
}
