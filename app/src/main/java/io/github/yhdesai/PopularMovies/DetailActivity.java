package io.github.yhdesai.PopularMovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
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
}
