package io.github.yhdesai.PopularMovies;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

import io.github.yhdesai.PopularMovies.adapter.MovieAdapter;
import io.github.yhdesai.PopularMovies.model.Bookmark;
import io.github.yhdesai.PopularMovies.model.BookmarkView;
import io.github.yhdesai.PopularMovies.model.Movie;
import io.github.yhdesai.PopularMovies.utils.JsonUtils;
import io.github.yhdesai.PopularMovies.utils.MovieUrlUtils;

import static io.github.yhdesai.PopularMovies.Constant.popular;
import static io.github.yhdesai.PopularMovies.Constant.topRated;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String CALLBACK_QUERY = "callbackQuery";
    private static final String CALLBACK_NAMESORT = "callbackNamesort";
    private String queryMovie = "popular";
    private String nameSort = "Popular Movies";
    private Movie[] mMovie = null;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private TextView tv_error;
    private Button btn_retry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_main);
        btn_retry = findViewById(R.id.btn_retry);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true);

        progressBar = findViewById(R.id.pb_main);
        tv_error = findViewById(R.id.tv_error);

        setTitle(nameSort);
        if (!isOnline()) {
            errorNetworkApi();
            return;
        }

        new MovieFetchTask().execute(queryMovie);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmark(view);
            }
        });
    }




    public void bookmark(View view) {
        Intent intent = new Intent(MainActivity.this, Bookmark.class);
        startActivity(intent);
    }
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private void errorNetworkApi() {
        progressBar.setVisibility(View.INVISIBLE);
        tv_error.setVisibility(View.VISIBLE);
        btn_retry.setVisibility(View.VISIBLE);
    }

    public void clickRetry(View view) {
        if (!isOnline()) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            view.startAnimation(shake);
            return;
        }
        queryMovie = "popular";
        btn_retry.setVisibility(View.INVISIBLE);
        tv_error.setVisibility(View.INVISIBLE);
        new MovieFetchTask().execute(queryMovie);
    }

    private void hideProgressAndTextview() {
        progressBar.setVisibility(View.INVISIBLE);
        tv_error.setVisibility(View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClickMovie(int position) {

        if (!isOnline()) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            errorNetworkApi();
            return;
        }

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("title", mMovie[position].getmTitle());
        intent.putExtra("poster", mMovie[position].getmMoviePoster());
        intent.putExtra("plot", mMovie[position].getmPlot());
        intent.putExtra("rating", mMovie[position].getmRating());
        intent.putExtra("releaseDate", mMovie[position].getmReleaseDate());
        intent.putExtra("backdropimage", mMovie[position].getBackdropPoster());
        intent.putExtra("id", mMovie[position].getmId());
        Log.i("mMovie class",mMovie[position].getClass().toString());
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String queryMovieSaved = queryMovie;
        String nameSortSaved = nameSort;
        outState.putString(CALLBACK_QUERY, queryMovieSaved);
        outState.putString(CALLBACK_NAMESORT, nameSortSaved);

    }



    private class MovieFetchTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mRecyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... strings) {
            if (!isOnline()) {
                errorNetworkApi();
                return null;
            }
            URL movieUrl = MovieUrlUtils.buildUrl(strings[0]);

            try {
                String movieResponse = MovieUrlUtils.getResponseFromHttp(movieUrl);
                Log.d("movieResponse", movieResponse);
                mMovie = JsonUtils.parseJsonMovie(movieResponse);
            } catch (Exception e) {

                e.printStackTrace();
            }
            return mMovie;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            new MovieFetchTask().cancel(true);
            if (movies != null) {
                mRecyclerView.setVisibility(View.VISIBLE);
                hideProgressAndTextview();
                //mMovie = movies;
                MovieAdapter movieAdapter = new MovieAdapter(movies, MainActivity.this, MainActivity.this);
                mRecyclerView.setAdapter(movieAdapter);

            } else {
                Log.e(LOG_TAG, "Problems with adapter");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isOnline()) return false;
        if (BuildConfig.ApiKey.equals("")) return false;
        int id = item.getItemId();
        switch (id) {
            case R.id.popularity:
                queryMovie = popular;
                new MovieFetchTask().execute(queryMovie);
                nameSort = "Popular Movies";
                setTitle(nameSort);
                break;
            case R.id.top_rated:
                queryMovie = topRated;
                new MovieFetchTask().execute(queryMovie);
                nameSort = "Top Rated Movies";
                setTitle(nameSort);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
