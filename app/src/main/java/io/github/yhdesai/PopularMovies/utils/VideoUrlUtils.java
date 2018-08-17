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



    public class VideoUrlUtils {

        public static URL buildUrl(String movieUrl) {
            Log.d("movieURl", movieUrl);

            Uri uri = Uri.parse(MOVIE_BASE_URL)
                    .buildUpon()
                    .appendPath(movieUrl)
                    .appendPath("videos")
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

        public static String getResponseFromHttpVideo(URL url) throws IOException {
            HttpURLConnection aurlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream ainputStream = aurlConnection.getInputStream();
                Scanner ascanner = new Scanner(ainputStream);
                ascanner.useDelimiter("\\A");

                boolean hasInput = ascanner.hasNext();
                if (hasInput) {
                    return ascanner.next();
                } else {
                    return null;
                }
            } finally {
                aurlConnection.disconnect();
            }
        }
    }

