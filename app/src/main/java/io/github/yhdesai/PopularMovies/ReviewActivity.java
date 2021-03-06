package io.github.yhdesai.PopularMovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import io.github.yhdesai.PopularMovies.adapter.ReviewAdapter;
import io.github.yhdesai.PopularMovies.model.MovieReview;
import io.github.yhdesai.PopularMovies.utils.JsonUtils;
import io.github.yhdesai.PopularMovies.utils.ReviewUrlUtils;

public class ReviewActivity extends AppCompatActivity {

    ListView mReviewListView;
    ReviewAdapter mReviewAdapter;
    private MovieReview[] mReview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        mReviewListView = findViewById(R.id.reviewListView);
        new ReviewsFetchTask().execute(getIntent().getStringExtra("id"));

    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class ReviewsFetchTask extends AsyncTask<String, Void, MovieReview[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected MovieReview[] doInBackground(String... strings) {
            if (!isOnline()) {
                return null;
            }


            URL reviewUrl = ReviewUrlUtils.buildUrl(strings[0]);


            try {
                String reviewReponse = ReviewUrlUtils.getResponseFromHttpVideo(reviewUrl);
                Log.d("trailer response", reviewReponse);
                mReview = JsonUtils.parseJsonReview(reviewReponse);

            } catch (Exception e) {

                e.printStackTrace();
            }
            return mReview;
        }


        @Override
        protected void onPostExecute(MovieReview[] review) {
            new ReviewsFetchTask().cancel(true);
            if (review != null) {


                List<MovieReview> reviews = Arrays.asList(review);
                mReviewAdapter = new ReviewAdapter(ReviewActivity.this, R.layout.item_review, reviews);
                mReviewListView.setAdapter(mReviewAdapter);

            } else {
                Log.e("detail", "Problems with adapter");
            }
        }

    }


}
