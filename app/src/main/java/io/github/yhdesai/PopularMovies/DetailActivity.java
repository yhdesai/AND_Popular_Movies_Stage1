package io.github.yhdesai.PopularMovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

import io.github.yhdesai.PopularMovies.model.MovieVideo;
import io.github.yhdesai.PopularMovies.utils.MovieJsonUtils;
import io.github.yhdesai.PopularMovies.utils.VideoUrlUtils;

public class DetailActivity extends AppCompatActivity {

    private MovieVideo[] mVideo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        ImageView iv_poster_detail = findViewById(R.id.id_small_movie_poster);
        TextView tv_title= findViewById(R.id.id_movie_name);
        TextView tv_plot = findViewById(R.id.id_movie_overview);
        TextView tv_rating = findViewById(R.id.id_user_rating);
        TextView tv_release = findViewById(R.id.movie_genre);


        String title = getIntent().getStringExtra("title");
        String poster = getIntent().getStringExtra("poster");
        String plot = getIntent().getStringExtra("plot");
        String rating = getIntent().getStringExtra("rating");
        String release = getIntent().getStringExtra("releaseDate");
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

        new VideoFetchTask().execute("videos");
        
    }


    private class VideoFetchTask extends AsyncTask<String, Void, MovieVideo[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected MovieVideo[] doInBackground(String... strings) {
            if (!isOnline()) {
                errorNetworkApi();
                return null;
            }
            URL movieUrl = VideoUrlUtils.buildUrl(strings[0]);
            Log.d("movieURL", movieUrl.toString());

            try {
                String movieResponse = VideoUrlUtils.getResponseFromHttp(movieUrl);
                Log.d("video response", movieResponse);
                mVideo = MovieJsonUtils.parseJsonVideo(movieResponse);
                Log.d("mVideo", mVideo.toString());
            } catch (Exception e) {

                e.printStackTrace();
            }
            return mVideo;
        }

        @Override
        protected void onPostExecute(MovieVideo[] video) {
            new VideoFetchTask().cancel(true);
            if (video != null) {



              Log.d("YAY", video.toString());

              /*  mRecyclerView.setVisibility(View.VISIBLE);*/
               /* hideProgressAndTextview();*/

             /*   mMovie = movies;
                MovieAdapter movieAdapter = new MovieAdapter(movies, DetailActivity.this, MainActivity.this);
                mRecyclerView.setAdapter(movieAdapter);*/

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
        /*progressBar.setVisibility(View.INVISIBLE);
        tv_error.setVisibility(View.VISIBLE);
        btn_retry.setVisibility(View.VISIBLE);*/
    }

}
