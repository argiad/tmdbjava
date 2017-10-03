package com.steegler.tmdb_java.utli;

import android.os.AsyncTask;
import android.util.Log;

import com.steegler.tmdb_java.BuildConfig;
import com.steegler.tmdb_java.interfaces.MovieCallBack;
import com.steegler.tmdb_java.model.Video;

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
import java.util.Locale;

/**
 * Retrieve Movie Detail Async task
 * Created by argi on 10/1/17.
 */

public class RetrieveMovieDetailTask extends AsyncTask {


    private MovieCallBack callBack;

    @Override
    protected Object doInBackground(Object[] objects) {


        StringBuilder builder = new StringBuilder();
        builder.append(BuildConfig.API_V3_URL); // main route
        builder.append(String.format("movie/%d/videos?", (int) objects[0])); // action
        builder.append(String.format("api_key=%s", BuildConfig.API_KEY_v3)); // API KEY
        builder.append(String.format("&language=%s", Locale.getDefault().getLanguage())); // Current system language
        Log.d("Request", builder.toString());

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
            callBack.updateData(result);
    }


    public void setCallBack(MovieCallBack callBack) {
        this.callBack = callBack;
    }

    private Object request(String request) throws IOException {

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

    private Object parseResult(String result) {
        Video video = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray array = (JSONArray) jsonObject.get("results");
            if (array.length() > 0) {
                JSONObject videoObject = array.getJSONObject(0);
                video = Video.build(videoObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return video;
    }

    private String stringify(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        return bufferedReader.readLine();
    }

}
