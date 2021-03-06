package com.steegler.tmdb_java.utli;

import android.os.AsyncTask;
import android.util.Log;

import com.steegler.tmdb_java.BuildConfig;
import com.steegler.tmdb_java.interfaces.MovieCallBack;
import com.steegler.tmdb_java.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Retrieve Movie and TV List AsyncTask
 *
 * Created by argi on 10/1/17.
 */

public class RetrieveMovieListTask extends AsyncTask {

    private int page = 0;
    private int totalResults = 0;

    private MovieCallBack callBack;

    @Override
    protected List<Movie> doInBackground(Object[] objects) {

        StringBuilder builder = new StringBuilder();
        builder.append(BuildConfig.API_V3_URL); // main route
        builder.append((objects.length >= 1 && objects[0] != null) ? (String) objects[0] : "discover/movie?"); // action
        builder.append(String.format("api_key=%s", BuildConfig.API_KEY_v3)); // API KEY
        builder.append((objects.length >= 2 && objects[1] != null) ? String.format("&query=%s", (String) objects[1]) : "&query=a"); // query
        builder.append((objects.length >= 3 && objects[2] != null) ? String.format("&page=%d", (int) objects[2]) : ""); // pager
        builder.append(String.format("&language=%s", Locale.getDefault().getLanguage())); // Current system language
        builder.append((objects.length >= 4 && objects[3] != null) ? String.format("&sort_by=%s", (String) objects[3]) : "");

        Log.d("Request", builder.toString());

        page = (objects.length >= 3 && objects[2] != null) ? (int) objects[2] : 0;

        try {
            return request(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        if (callBack != null)
            callBack.updateData((List<Movie>) result, page < 2, totalResults);
    }

    public void setCallBack(MovieCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     *
     * @param request url request string
     * @return
     * @throws IOException
     */
    private List<Movie> request(String request) throws IOException {

        URL url = new URL(request);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(5000);
        urlConnection.setConnectTimeout(10000);
        urlConnection.setRequestMethod("GET");
        urlConnection.addRequestProperty("Accept", "application/json");
        urlConnection.setDoInput(true);
        urlConnection.connect();

        try (InputStream inputStream = urlConnection.getInputStream()) {
            String result = stringify(inputStream);
            Log.d("Async", result);
            inputStream.close();
            return parseResult(result);
        }
    }

    /**
     *
     * @param result JSON string
     * @return
     */
    private List<Movie> parseResult(String result) {
        String streamAsString = result;
        List<Movie> results = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(streamAsString);
            totalResults = jsonObject.optInt("total_results");
            JSONArray array = (JSONArray) jsonObject.get("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonMovieObject = array.getJSONObject(i);
                Movie movie = Movie.build(jsonMovieObject);
                results.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     *  Input stream into String
     * @param stream InputStream
     * @return
     * @throws IOException
     */
    private String stringify(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        return bufferedReader.readLine();
    }


}
