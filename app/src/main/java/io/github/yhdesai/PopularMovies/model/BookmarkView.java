package io.github.yhdesai.PopularMovies.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import io.github.yhdesai.PopularMovies.R;
import io.github.yhdesai.PopularMovies.ReviewActivity;


public class BookmarkView extends AppCompatActivity {

    String releaseDate;
    String title;
    String plot;
    String rating;
    String releaseFinal;
    TextView tv_title;
    TextView tv_plot;
    TextView tv_rating;
    TextView tv_release;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_view);
        initViews();


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

    private void initViews() {
        tv_title = findViewById(R.id.id_movie_name);
        tv_plot = findViewById(R.id.id_movie_overview);
        tv_rating = findViewById(R.id.id_user_rating);
        tv_release = findViewById(R.id.movie_genre);
    }


    public void reviews(View view) {
        Intent intent = new Intent(BookmarkView.this, ReviewActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}




