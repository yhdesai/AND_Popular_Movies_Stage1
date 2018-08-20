package io.github.yhdesai.PopularMovies.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import io.github.yhdesai.PopularMovies.R;
import io.github.yhdesai.PopularMovies.ReviewActivity;
import io.github.yhdesai.PopularMovies.data.AppDatabase;
import io.github.yhdesai.PopularMovies.data.BookmarkEntry;


public class BookmarkView extends AppCompatActivity {

        private static final String DEFAULT_TASK_ID = "-1";
        private static final String TAG = io.github.yhdesai.PopularMovies.DetailActivity.class.getSimpleName();
        String releaseDate;
        EditText mEditText;
        RadioGroup mRadioGroup;
        String mBookmarkId = DEFAULT_TASK_ID;
        private MovieReview[] mReview = null;
        private MovieTrailer mTrailer = null;
        private Movie movie;
        private String id;
    String title;
    String plot;
    String rating;
        private BookmarkEntry bookmark;
        private AppDatabase mDb;
    String releaseFinal;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bookmark_view);
            initViews();


            ImageView iv_poster_detail = findViewById(R.id.id_small_movie_poster);
            TextView tv_title = findViewById(R.id.id_movie_name);
            TextView tv_plot = findViewById(R.id.id_movie_overview);
            TextView tv_rating = findViewById(R.id.id_user_rating);
            TextView tv_release = findViewById(R.id.movie_genre);


            title = getIntent().getStringExtra("title");
            plot = getIntent().getStringExtra("plot");
            rating = getIntent().getStringExtra("rating");
            releaseDate = getIntent().getStringExtra("date");
            id = getIntent().getStringExtra("id");
            releaseFinal = releaseDate.substring(0, 4);


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



        private void initViews() {
            mEditText = findViewById(R.id.editTextTaskDescription);
            mRadioGroup = findViewById(R.id.radioGroup);


        }

        public void reviews(View view) {
            Intent intent = new Intent(BookmarkView.this, ReviewActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
    }




