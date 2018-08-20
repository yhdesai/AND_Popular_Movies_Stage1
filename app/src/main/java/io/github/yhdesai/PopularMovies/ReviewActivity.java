package io.github.yhdesai.PopularMovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.yhdesai.PopularMovies.adapter.MovieAdapter;
import io.github.yhdesai.PopularMovies.adapter.ReviewAdapter;
import io.github.yhdesai.PopularMovies.model.MovieReview;
import io.github.yhdesai.PopularMovies.utils.JsonUtils;
import io.github.yhdesai.PopularMovies.utils.ReviewUrlUtils;

//TODO redesign the item_review activity
public class ReviewActivity extends AppCompatActivity {

    private MovieReview[] mReview = null;


    ListView mReviewListView;
    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        mReviewListView = findViewById(R.id.reviewListView);
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


            URL reviewUrl = ReviewUrlUtils.buildUrl(strings[0]);


            try {
                String reviewReponse = ReviewUrlUtils.getResponseFromHttpVideo(reviewUrl);
                Log.d("trailer response", reviewReponse);
                mReview = JsonUtils.parseJsonReview(reviewReponse);
                Log.d("mTrailer", mReview.toString());

            } catch (Exception e) {

                e.printStackTrace();
            }
            return mReview;
        }


        @Override
        protected void onPostExecute(MovieReview[] review) {
            new ReviewsFetchTask().cancel(true);
            if (review != null) {

                // Initialize message ListView and its adapter
                //List<MovieReview> friendlyMessages = new ArrayList<>();
                List<MovieReview> reviews = Arrays.asList(review);
                mReviewAdapter = new ReviewAdapter(ReviewActivity.this, R.layout.item_review, reviews);
                mReviewListView.setAdapter(mReviewAdapter);



                /*      mReviewAdapter.add();
                 */


                Log.d("class#################", review.getClass().toString());


                Log.d("review result", review.toString());
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
}
