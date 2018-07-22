package io.github.yhdesai.PopularMovies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.github.yhdesai.PopularMovies.model.Movie;

import static io.github.yhdesai.PopularMovies.Constant.MOVIE_PLOT;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_POSTER;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_RATING;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_RELEASE_DATE;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_RESULTS;
import static io.github.yhdesai.PopularMovies.Constant.MOVIE_TITLE;
import static io.github.yhdesai.PopularMovies.Constant.Movie_Backdrop;


public class MovieJsonUtils {







    public static Movie[] parseJsonMovie(String jsonMoviesData) throws JSONException {

        JSONObject jsonRoot = new JSONObject(jsonMoviesData);
        JSONArray jsonArrayResult = jsonRoot.getJSONArray(MOVIE_RESULTS);
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

}
