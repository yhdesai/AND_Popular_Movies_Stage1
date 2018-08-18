package io.github.yhdesai.PopularMovies.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.github.yhdesai.PopularMovies.model.Movie;
import io.github.yhdesai.PopularMovies.model.MovieReview;
import io.github.yhdesai.PopularMovies.model.MovieTrailer;

import static io.github.yhdesai.PopularMovies.Constant.MOVIE_PLOT;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_POSTER;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_RATING;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_RELEASE_DATE;
import static io.github.yhdesai.PopularMovies.Constant.RESULTS;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_TITLE;
import static io.github.yhdesai.PopularMovies.Constant.Movie_Backdrop;
import static io.github.yhdesai.PopularMovies.Constant.REVIEW_AUTHOR;
import static io.github.yhdesai.PopularMovies.Constant.REVIEW_CONTENT;
import static io.github.yhdesai.PopularMovies.Constant.REVIEW_ID;
import static io.github.yhdesai.PopularMovies.Constant.REVIEW_URL;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_ID;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_ISO_1;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_ISO_2;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_KEY;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_NAME;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_SITE;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_SIZE;
import static io.github.yhdesai.PopularMovies.Constant.VIDEO_TYPE;


public class JsonUtils {

    public static Movie[] parseJsonMovie(String jsonMoviesData) throws JSONException {

        JSONObject jsonRoot = new JSONObject(jsonMoviesData);
        JSONArray jsonArrayResult = jsonRoot.getJSONArray(RESULTS);
        Movie[] result = new Movie[jsonArrayResult.length()];
        for (int i = 0; i < jsonArrayResult.length(); i++) {
            Movie movie = new Movie();
            movie.setmId(jsonArrayResult.getJSONObject(i).optString(VIDEO_ID));
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

    public static MovieTrailer parseJsonTrailer(String jsonVideosData) throws JSONException {
        Log.d("jsondata", jsonVideosData);
        JSONObject jsonRoot = new JSONObject(jsonVideosData);
        JSONArray jsonArrayResult = jsonRoot.getJSONArray("results");
        MovieTrailer result = new MovieTrailer();
        //for (int i = 0; i < jsonArrayResult.length(); i++) {
        MovieTrailer movieVideo = new MovieTrailer();
        movieVideo.setvID(jsonArrayResult.getJSONObject(0).optString(VIDEO_ID));
        movieVideo.setvISO639(jsonArrayResult.getJSONObject(0).optString(VIDEO_ISO_1));
        movieVideo.setvISO3166(jsonArrayResult.getJSONObject(0).optString(VIDEO_ISO_2));
        movieVideo.setvKey(jsonArrayResult.getJSONObject(0).optString(VIDEO_KEY));
        movieVideo.setvName(jsonArrayResult.getJSONObject(0).optString(VIDEO_NAME));
        movieVideo.setvSite((jsonArrayResult.getJSONObject(0).optString(VIDEO_SITE)));
        movieVideo.setvSize(jsonArrayResult.getJSONObject(0).optInt(VIDEO_SIZE));
        movieVideo.setvType(jsonArrayResult.getJSONObject(0).optString(VIDEO_TYPE));
        return movieVideo;
    }

    public static MovieReview[] parseJsonReview(String jsonReviewData) throws JSONException {
        Log.d("jsondata", jsonReviewData);
        JSONObject jsonRoot = new JSONObject(jsonReviewData);
        JSONArray jsonArrayResult = jsonRoot.getJSONArray("results");
        MovieReview[] result = new MovieReview[jsonArrayResult.length()];
        for (int i = 0; i < jsonArrayResult.length(); i++) {
            MovieReview movieReview = new MovieReview();
            movieReview.setrAuthor(jsonArrayResult.getJSONObject(0).optString(REVIEW_AUTHOR));
            movieReview.setrContent(jsonArrayResult.getJSONObject(0).optString(REVIEW_CONTENT));
            movieReview.setrId(jsonArrayResult.getJSONObject(0).optString(REVIEW_ID));
            movieReview.setrUrl(jsonArrayResult.getJSONObject(0).optString(REVIEW_URL));
            result[i] = movieReview;
        }
        return result;

    }
}
