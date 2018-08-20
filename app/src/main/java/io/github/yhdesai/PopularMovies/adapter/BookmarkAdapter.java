/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.yhdesai.PopularMovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.yhdesai.PopularMovies.Constant;
import io.github.yhdesai.PopularMovies.R;
import io.github.yhdesai.PopularMovies.data.BookmarkEntry;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";


    final private ItemClickListener mItemClickListener;
    private List<BookmarkEntry> mBookmarkEntries;
    private Context mContext;

    public BookmarkAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    @Override
    public BookmarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the bookmark_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.bookmark_layout, parent, false);

        return new BookmarkViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {

        BookmarkEntry bookmarkEntry = mBookmarkEntries.get(position);
        if (bookmarkEntry != null) {
            String plot = bookmarkEntry.getPlot();
            String rating = bookmarkEntry.getRating();
            String title = bookmarkEntry.getTitle();
            // String backgroundPoster = bookmarkEntry.getBackdropPoster();
            String moviePoster = bookmarkEntry.getMoviePoster();
            String id = bookmarkEntry.getId();
            String releaseDate = bookmarkEntry.getReleaseDate();
            /*String updatedAt = dat
           eFormat.format(bookmarkEntry.getUpdatedAt());
             */


            Log.d("BokmarkEntry",
                    "\n" + "\n" + "id: " + id + "\n" +
                            "title: " + title + "\n" +
                            "poster: " + moviePoster + "\n" +
                            "plot: " + plot + "\n" +
                            "rating: " + rating + "\n"/*+
                            "date: "+releaseFinal+ "\n"+
                            "poster: "+backdropPoster+"\n"*/
            );
            //Set values
            /*if (plot != null) {

                holder.plot.setText(plot);
            }
            if (rating != null) {
                holder.rating.setText(rating);
            }*/
            if (title != null) {
                holder.title.setText(title);
            }
//        holder.backdropPoster.setText(backgroundPoster);

            Picasso.with(mContext)
                    .load(Constant.URL_IMAGE_PATH.concat(moviePoster))
                    .into(holder.moviePoster);

            /* holder.moviePoster.setImageURI(moviePoster);*/

           /* if (releaseDate != null) {
                holder.releastDate.setText(releaseDate);
            }*/
            /*holder.updatedAtView.setText(updatedAt);*/

       /* // Programmatically set the text and color for the priority TextView
        String priorityString = "" + rating; // converts int to String
        holder.priorityView.setText(priorityString);
*//*
        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
       */ // Get the appropriate background color based on the priority

        }
    }

    /*
    Helper method for selecting the correct priority circle color.
    P1 = red, P2 = orange, P3 = yellow
    */
  /*  private int getPriorityColor(String priority) {
        String priorityColor = "0";

        switch (priority) {
            case "1":
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case "2":
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case "3":
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }*/

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mBookmarkEntries == null) {
            return 0;
        }
        return mBookmarkEntries.size();
    }

    public List<BookmarkEntry> getBookmarks() {
        return mBookmarkEntries;
    }


    public void setBookmarks(List<BookmarkEntry> bookmarkEntries) {
        mBookmarkEntries = bookmarkEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(String itemId, String title, String plot, String rating, String releaseDate);
    }

    class BookmarkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        /* TextView releastDate;
         TextView rating;
         TextView plot;*/
        ImageView moviePoster;


        TextView id;
        //   TextView backdropPoster;


        public BookmarkViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.movie_name);
           /* releastDate = itemView.findViewById(R.id.movie_genre);
            rating = itemView.findViewById(R.id.ratingID);
            plot = itemView.findViewById(R.id.id_movie_overview);*/
            moviePoster = itemView.findViewById(R.id.id_small_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String elementId = mBookmarkEntries.get(getAdapterPosition()).getId();
            String title = mBookmarkEntries.get(getAdapterPosition()).getTitle();
            String plot = mBookmarkEntries.get(getAdapterPosition()).getPlot();
            String rating = mBookmarkEntries.get(getAdapterPosition()).getRating();
            String releaseDate = mBookmarkEntries.get(getAdapterPosition()).getReleaseDate();
            mItemClickListener.onItemClickListener(elementId, title, plot, rating, releaseDate);
        }
    }
}