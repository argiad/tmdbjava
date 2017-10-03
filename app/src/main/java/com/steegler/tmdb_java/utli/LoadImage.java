package com.steegler.tmdb_java.utli;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Simple Async ImageLoader
 *
 * Created by argi on 10/1/17.
 */


public class LoadImage extends AsyncTask<String, Void, Bitmap> {

    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/%s";

    private final WeakReference<ImageView> imageViewReference;

    public LoadImage(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        if (params.length > 0)
            try {
                String url = String.format(IMAGE_URL, (String) params[0]);
                return downloadBitmap(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        ImageView imageView = imageViewReference.get();
        if (imageView != null) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Drawable placeholder = imageView.getContext().getResources().getDrawable(android.R.drawable.ic_menu_report_image);
                imageView.setImageDrawable(placeholder);
            }
        }
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
