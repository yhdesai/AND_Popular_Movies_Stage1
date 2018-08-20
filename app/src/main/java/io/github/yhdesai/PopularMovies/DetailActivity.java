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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

import io.github.yhdesai.PopularMovies.bookmark.AddBookmarkViewModelFactory;
import io.github.yhdesai.PopularMovies.bookmark.AppExecutors;
import io.github.yhdesai.PopularMovies.data.AppDatabase;
import io.github.yhdesai.PopularMovies.data.BookmarkEntry;
import io.github.yhdesai.PopularMovies.model.AddBookmarkViewModel;
import io.github.yhdesai.PopularMovies.model.Movie;
import io.github.yhdesai.PopularMovies.model.MovieReview;
import io.github.yhdesai.PopularMovies.model.MovieTrailer;
import io.github.yhdesai.PopularMovies.utils.JsonUtils;
import io.github.yhdesai.PopularMovies.utils.VideoUrlUtils;

public class DetailActivity extends AppCompatActivity {

    private static final String CONTENT_AUTHORITY = "io.github.yhdesai.PopularMovies";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final String DEFAULT_TASK_ID = "-1";
    private static final String TAG = DetailActivity.class.getSimpleName();
    String releaseDate;
    EditText mEditText;
    RadioGroup mRadioGroup;
    String mBookmarkId = DEFAULT_TASK_ID;
    private MovieReview[] mReview = null;
    private MovieTrailer mTrailer = null;
    private Movie movie;
    private String id;
    private String title;
    private String moviePoster;
    private String plot;
    private String rating;
    private String backdropPoster;
    private BookmarkEntry bookmark;
    private AppDatabase mDb;
    private AppDatabase mDbs;
    private String releaseFinal;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());

        AddBookmarkViewModelFactory factory = new AddBookmarkViewModelFactory(mDb, mBookmarkId);
        final AddBookmarkViewModel viewModel
                = ViewModelProviders.of(this, factory).get(AddBookmarkViewModel.class);

        viewModel.getBookmark().observe(this,
                new Observer<BookmarkEntry>() {
                    @Override
                    public void onChanged(@Nullable BookmarkEntry taskEntry) {
                        viewModel.getBookmark().removeObserver(this);
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

        Picasso.with(this)
                .load(Constant.URL_IMAGE_PATH.concat(moviePoster))
                .into(iv_poster_detail);
        tv_title.setText(title);
        tv_plot.setText(plot);
        tv_rating.setText(rating.concat("/10"));
        tv_release.setText(releaseFinal);
        setTitle(title);
    }

    private void populateUI(BookmarkEntry task) {
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
        Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void bookmark(View view) {

        Log.d("###data here###",
                "\n" + "\n" + "id: " + id + "\n" +
                        "title: " + title + "\n" +
                        "poster: " + moviePoster + "\n" +
                        "plot: " + plot + "\n" +
                        "rating: " + rating + "\n" +
                        "date: " + releaseFinal + "\n" +
                        "poster: " + backdropPoster + "\n"
        );

        bookmark = new BookmarkEntry(id, title, moviePoster, plot, rating, releaseFinal, backdropPoster);
        // mDb.taskDao().insertBookmark(task);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mBookmarkId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.bookmarkDao().insertBookmark(bookmark);
                } else {
                    //update task
                    bookmark.setId(mBookmarkId);
                    mDb.bookmarkDao().updateBookmark(bookmark);
                }
                /* finish();*/
            }
        });
        //Need help here for this todo, before adding the bookmark, it needs to check if it already exists
        //TODO add checker for db entry here

       /* mDbs = AppDatabase.getInstance(getApplicationContext());

        AddBookmarkViewModelFactory factory = new AddBookmarkViewModelFactory(mDb, mBookmarkId);
        final AddBookmarkViewModel viewModel
                = ViewModelProviders.of(DetailActivity.this, factory).get(AddBookmarkViewModel.class);

        viewModel.getBookmark().observe(DetailActivity.this, new Observer<BookmarkEntry>() {
            @Override
            public void onChanged(@NonNull BookmarkEntry bookmarkEntry) {
                viewModel.getBookmark().removeObserver(this);
                Log.d("bookmarkEntry ID", bookmarkEntry.getId());
            }
        });*/


      /*  final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath("movies")
                .build();
*/
              /*  if ("r" == "g") {
                    // not found in database
                    getContentResolver().query("content://com.foo.android.providerdemo/idOfItem>");

                    mDb.bookmarkDao().insertBookmark(bookmark);
                } else {
                    mDb.bookmarkDao().deleteBookmark(bookmark);
                }*/

    }
   /* AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mBookmarkId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.taskDao().insertBookmark(task);
                } else {
                    //update task
                    task.setId(mBookmarkId);
                    mDb.taskDao().updateBookmark(task);
                }
                finish();
            }
        });*/


    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private class TrailerFetchTask extends AsyncTask<String, Void, MovieTrailer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected MovieTrailer doInBackground(String... strings) {
            if (!isOnline()) {
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


}


