package io.github.yhdesai.PopularMovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import io.github.yhdesai.PopularMovies.R;
import io.github.yhdesai.PopularMovies.model.MovieReview;

public class ReviewAdapter extends ArrayAdapter<MovieReview> {
    public ReviewAdapter(Context context, int resource, List<MovieReview> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_review, parent, false);
        }

        TextView author = convertView.findViewById(R.id.author);
        TextView content = convertView.findViewById(R.id.content);
        TextView id = convertView.findViewById(R.id.id);
        TextView url = convertView.findViewById(R.id.url);

        MovieReview message = getItem(position);

        author.setText(message.getrAuthor());
        content.setText(message.getrContent());
        id.setText(message.getrId());
        url.setText(message.getrUrl());


        return convertView;
    }
}

