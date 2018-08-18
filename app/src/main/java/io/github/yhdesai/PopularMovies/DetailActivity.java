package io.github.yhdesai.PopularMovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

import io.github.yhdesai.PopularMovies.adapter.MovieAdapter;
import io.github.yhdesai.PopularMovies.model.MovieReview;
import io.github.yhdesai.PopularMovies.model.MovieTrailer;
import io.github.yhdesai.PopularMovies.utils.JsonUtils;
import io.github.yhdesai.PopularMovies.utils.ReviewUrlUtils;
import io.github.yhdesai.PopularMovies.utils.VideoUrlUtils;

public class DetailActivity extends AppCompatActivity {

    private MovieReview[] mReview = null;
    private MovieTrailer mTrailer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView iv_poster_detail = findViewById(R.id.id_small_movie_poster);
        TextView tv_title = findViewById(R.id.id_movie_name);
        TextView tv_plot = findViewById(R.id.id_movie_overview);
        TextView tv_rating = findViewById(R.id.id_user_rating);
        TextView tv_release = findViewById(R.id.movie_genre);


        String title = getIntent().getStringExtra("title");
        String poster = getIntent().getStringExtra("poster");
        String plot = getIntent().getStringExtra("plot");
        String rating = getIntent().getStringExtra("rating");
        String release = getIntent().getStringExtra("releaseDate");
        String id = getIntent().getStringExtra("id");
        String releaseFinal = release.substring(0, 4);

        Picasso.with(this)
                .load(Constant.URL_IMAGE_PATH.concat(poster))
                .into(iv_poster_detail);
        tv_title.setText(title);
        tv_plot.setText(plot);
        tv_rating.setText(rating.concat("/10"));
        tv_release.setText(releaseFinal);
        setTitle(title);
    }

    public void trailer(View view) {

        new TrailerFetchTask().execute(getIntent().getStringExtra("id"));

    }

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public void reviews(View view) {
        new ReviewsFetchTask().execute(getIntent().getStringExtra("id"));
    }

    private class ReviewsFetchTask extends AsyncTask<String, Void, MovieReview[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected MovieReview[] doInBackground(String... strings) {
            if (!isOnline()) {
                errorNetworkApi();
                return null;
            }
            Log.d("first element", strings[0]);

            URL reviewUrl = ReviewUrlUtils.buildUrl(strings[0]);


            try {
                String trailerResponse = ReviewUrlUtils.getResponseFromHttpVideo(reviewUrl);
                Log.d("trailer response", trailerResponse);
                mReview = JsonUtils.parseJsonReview(trailerResponse);
                Log.d("mTrailer", mReview.toString());

            } catch (Exception e) {

                e.printStackTrace();
            }
            return mReview;
        }


        @Override
        protected void onPostExecute(MovieReview[] review) {
            new DetailActivity.TrailerFetchTask().cancel(true);
            if (review != null) {
                //TODO open new activity and show the result there
                //ReviewAdapter reviewAdapter = new ReviewAdapter(review, DetailActivity.this, DetailActivity.this);
               // mRecyclerView.setAdapter(reviewAdapter);
                Log.d("review result", review.toString());
            } else {
                Log.e("detail", "Problems with adapter");
            }
        }

    }


    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$


    private class TrailerFetchTask extends AsyncTask<String, Void, MovieTrailer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected MovieTrailer doInBackground(String... strings) {
            if (!isOnline()) {
                errorNetworkApi();
                return null;
            }
            Log.d("first element", strings[0]);

            URL trailerUrl = VideoUrlUtils.buildUrl(strings[0]);

            Log.d("movieURL", trailerUrl.toString());

            try {
                String trailerResponse = VideoUrlUtils.getResponseFromHttpVideo(trailerUrl);
                Log.d("trailer response", trailerResponse);
                mTrailer = JsonUtils.parseJsonTrailer(trailerResponse);
                Log.d("mTrailer", mTrailer.toString());

            } catch (Exception e) {

                e.printStackTrace();
            }
            return mTrailer;
        }


        @Override
        protected void onPostExecute(MovieTrailer video) {
            new TrailerFetchTask().cancel(true);
            if (video != null) {
                String key = video.getvKey();
                watchYoutubeVideo(DetailActivity.this, key);
            } else {
                Log.e("detail", "Problems with adapter");
            }
        }

    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private void errorNetworkApi() {
    }

    public static void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

}
