package io.github.yhdesai.PopularMovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.Date;

import io.github.yhdesai.PopularMovies.adapter.MovieAdapter;
import io.github.yhdesai.PopularMovies.bookmark.AddTaskActivity;
import io.github.yhdesai.PopularMovies.bookmark.AddTaskViewModel;
import io.github.yhdesai.PopularMovies.bookmark.AddTaskViewModelFactory;
import io.github.yhdesai.PopularMovies.bookmark.AppExecutors;
import io.github.yhdesai.PopularMovies.bookmark.MainActivity2;
import io.github.yhdesai.PopularMovies.data.AppDatabase;
import io.github.yhdesai.PopularMovies.data.TaskEntry;
import io.github.yhdesai.PopularMovies.model.Movie;
import io.github.yhdesai.PopularMovies.model.MovieReview;
import io.github.yhdesai.PopularMovies.model.MovieTrailer;
import io.github.yhdesai.PopularMovies.utils.JsonUtils;
import io.github.yhdesai.PopularMovies.utils.ReviewUrlUtils;
import io.github.yhdesai.PopularMovies.utils.VideoUrlUtils;

public class DetailActivity extends AppCompatActivity {

    private MovieReview[] mReview = null;
    private MovieTrailer mTrailer = null;

    private Movie movie;

    private String id;
    private String title;
    private String moviePoster;
    private String plot;
    private String rating;
    private String releaseDate;
    private String backdropPoster;


    private TaskEntry task;

    EditText mEditText;
    RadioGroup mRadioGroup;




    private static final String DEFAULT_TASK_ID = "-1";
    // Constant for logging
    private static final String TAG = DetailActivity.class.getSimpleName();


    private String mTaskId = DEFAULT_TASK_ID;


    private AppDatabase mDb;
    private String releaseFinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());

        AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mDb, mTaskId);
        // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
        // for that use the factory created above AddTaskViewModel
        final AddTaskViewModel viewModel
                = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

        viewModel.getTask().observe(this, new Observer<TaskEntry>() {
            @Override
            public void onChanged(@Nullable TaskEntry taskEntry) {
                viewModel.getTask().removeObserver(this);
                populateUI(taskEntry);
            }
        });


        ImageView iv_poster_detail = findViewById(R.id.id_small_movie_poster);
        TextView tv_title = findViewById(R.id.id_movie_name);
        TextView tv_plot = findViewById(R.id.id_movie_overview);
        TextView tv_rating = findViewById(R.id.id_user_rating);
        TextView tv_release = findViewById(R.id.movie_genre);


        title = getIntent().getStringExtra("title");
        moviePoster = getIntent().getStringExtra("poster");
        plot = getIntent().getStringExtra("plot");
        rating = getIntent().getStringExtra("rating");
        releaseDate = getIntent().getStringExtra("releaseDate");
        id = getIntent().getStringExtra("id");
        releaseFinal = releaseDate.substring(0, 4);
        backdropPoster = getIntent().getStringExtra("backdropimage");

       /* movie.setmTitle(title);
        movie.setmMoviePoster(poster);
        movie.setmPlot(plot);
        movie.setmRating(rating);
        movie.setmReleaseDate(release);
        movie.setmId(id);
        movie.setmReleaseDate(releaseFinal);*/

        Picasso.with(this)
                .load(Constant.URL_IMAGE_PATH.concat(moviePoster))
                .into(iv_poster_detail);
        tv_title.setText(title);
        tv_plot.setText(plot);
        tv_rating.setText(rating.concat("/10"));
        tv_release.setText(releaseFinal);
        setTitle(title);
    }
    private void populateUI(TaskEntry task) {
        if (task == null) {
            return;
        }

        mEditText.setText(task.getPlot());
        ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
    }
    public void trailer(View view) {

        new TrailerFetchTask().execute(getIntent().getStringExtra("id"));

    }
    private void initViews() {
        mEditText = findViewById(R.id.editTextTaskDescription);
        mRadioGroup = findViewById(R.id.radioGroup);


    }


    public void reviews(View view) {
        // new ReviewsFetchTask().execuintent.putExtra("plot", mMovie[position].getmPlot());te(getIntent().getStringExtra("id"));
        Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void bookmark(View view) {
        /*Intent intent = new Intent(DetailActivity.this, AddTaskActivity.class);
        startActivity(intent);*/
       /* String id = "1";
        String title= "title";
        String moviePoster="test";
        String plot="plot";
        String rating="rating";
        String releasedate="date";
        String backdropPoster="poster";*/


        Log.d("###data here###",
                "\n"+"\n"+"id: "+ id +"\n"+
                        "title: "+title+ "\n"+
                        "poster: "+ moviePoster+"\n"+
                        "plot: "+ plot+"\n"+
                        "rating: "+rating+"\n"+
                        "date: "+releaseFinal+ "\n"+
                        "poster: "+backdropPoster+"\n"
                        );

        task = new TaskEntry(id, title, moviePoster, plot, rating, releaseFinal, backdropPoster);
       // mDb.taskDao().insertTask(task);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                    mDb.taskDao().insertTask(task);


            }
        });
       /* AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.taskDao().insertTask(task);
                } else {
                    //update task
                    task.setId(mTaskId);
                    mDb.taskDao().updateTask(task);
                }
                finish();
            }
        });*/

    }

    public void bookmark1(View view) {
        Intent intent = new Intent(DetailActivity.this, MainActivity2.class);
        startActivity(intent);
    }

  /*  private class ReviewsFetchTask extends AsyncTask<String, Void, MovieReview[]> {

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

    }*/

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



   /* private void bookmark(View view) {
        mDb = AppDatabase.getInstance(getApplicationContext());

        mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

        AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mDb, mTaskId);
        // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
        // for that use the factory created above AddTaskViewModel
        final AddTaskViewModel viewModel
                = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

        // COMPLETED (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
        viewModel.getTask().observe(this, new Observer<TaskEntry>() {
            @Override
            public void onChanged(@Nullable TaskEntry taskEntry) {
                viewModel.getTask().removeObserver(this);
                populateUI(taskEntry);
            }
        });
    }*/
}


