package io.github.yhdesai.PopularMovies.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.github.yhdesai.PopularMovies.model.Movie;
import io.github.yhdesai.PopularMovies.model.MovieVideo;

import static io.github.yhdesai.PopularMovies.Constant.MOVIE_PLOT;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_POSTER;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_RATING;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_RELEASE_DATE;
import static io.github.yhdesai.PopularMovies.Constant.RESULTS;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_TITLE;
import static io.github.yhdesai.PopularMovies.Constant.Movie_Backdrop;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_ID;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_ISO_1;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_ISO_2;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_KEY;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_NAME;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_SITE;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_SIZE;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_TYPE;


public class MovieJsonUtils {







    public static Movie[] parseJsonMovie(String jsonMoviesData) throws JSONException {

        JSONObject jsonRoot = new JSONObject(jsonMoviesData);
        JSONArray jsonArrayResult = jsonRoot.getJSONArray(RESULTS);
        Movie[] result = new Movie[jsonArrayResult.length()];
        for (int i = 0; i < jsonArrayResult.length(); i++) {
            Movie movie = new Movie();
            movie.setmTitle(jsonArrayResult.getJSONObject(i).optString(MOVIE_TITLE));
            movie.setmMoviePoster(jsonArrayResult.getJSONObject(i).optString(MOVIE_POSTER));
            movie.setmPlot(jsonArrayResult.getJSONObject(i).optString(MOVIE_PLOT));
            movie.setmRating(jsonArrayResult.getJSONObject(i).optString(MOVIE_RATING));
            movie.setmReleaseDate(jsonArrayResult.getJSONObject(i).optString(MOVIE_RELEASE_DATE));
            movie.setBackdropPoster((jsonArrayResult.getJSONObject(i).optString(Movie_Backdrop)));
            result[i] = movie;
        }
        return result;
    }

    public static MovieVideo[] parseJsonVideo(String jsonVideosData) throws JSONException{
        Log.d("jsondata", jsonVideosData);
        JSONObject jsonRoot = new JSONObject(jsonVideosData);
        JSONArray jsonArrayResult = jsonRoot.getJSONArray("results");
        MovieVideo[] result = new MovieVideo[jsonArrayResult.length()];
        for (int i = 0; i < jsonArrayResult.length(); i++) {
            MovieVideo movieVideo = new MovieVideo();
            movieVideo.setvID(jsonArrayResult.getJSONObject(i).optString(VIDEO_ID));
            movieVideo.setvISO639(jsonArrayResult.getJSONObject(i).optString(VIDEO_ISO_1));
            movieVideo.setvISO3166(jsonArrayResult.getJSONObject(i).optString(VIDEO_ISO_2));
            movieVideo.setvKey(jsonArrayResult.getJSONObject(i).optString(VIDEO_KEY));
            movieVideo.setvName(jsonArrayResult.getJSONObject(i).optString(VIDEO_NAME));
            movieVideo.setvSite((jsonArrayResult.getJSONObject(i).optString(VIDEO_SITE)));
            movieVideo.setvSize(jsonArrayResult.getJSONObject(i).optInt(VIDEO_SIZE));
            movieVideo.setvType(jsonArrayResult.getJSONObject(i).optString(VIDEO_TYPE));


            result[i] = movieVideo;
        }
        return result;
    }
}
