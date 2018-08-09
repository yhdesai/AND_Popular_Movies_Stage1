package io.github.yhdesai.PopularMovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import io.github.yhdesai.PopularMovies.BuildConfig;
import io.github.yhdesai.PopularMovies.Constant;

import static io.github.yhdesai.PopularMovies.Constant.MOVIE_BASE_URL;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_TEMP_URL;


public class MovieUrlUtils {



    public static URL buildUrl(String movieUrl) {

        Uri uri = Uri.parse(MOVIE_BASE_URL)
                .buildUpon()
                .appendPath(movieUrl)
                .appendQueryParameter(Constant.MOVIE_QUERY_API, BuildConfig.ApiKey)
                .build();
        Log.d("url", uri.toString());
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e("MovieUrlUtils", "Problems create url", e);
        }

        return url;
    }

    public static String getResponseFromHttp(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
