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

package io.github.yhdesai.PopularMovies.bookmark;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import io.github.yhdesai.PopularMovies.R;
import io.github.yhdesai.PopularMovies.data.TaskEntry;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";


    final private ItemClickListener mItemClickListener;
    private List<TaskEntry> mTaskEntries;
    private Context mContext;

    public TaskAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);

        return new TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        TaskEntry taskEntry = mTaskEntries.get(position);
        String plot = taskEntry.getPlot();
        String rating = taskEntry.getRating();
        String title = taskEntry.getTitle();
        String backgroundPoster = taskEntry.getBackdropPoster();
        String moviePoster = taskEntry.getMoviePoster();
        String id = taskEntry.getId();
        String releaseDate= taskEntry.getReleaseDate();
        /*String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());
*/
        //Set values
        holder.plot.setText(plot);
        holder.rating.setText(rating);
        holder.title.setText(title);
        holder.backdropPoster.setText(backgroundPoster);
        holder.moviePoster.setText(moviePoster);
        holder.id.setText(id);
        holder.releastDate.setText(releaseDate);
        /*holder.updatedAtView.setText(updatedAt);*/

       /* // Programmatically set the text and color for the priority TextView
        String priorityString = "" + rating; // converts int to String
        holder.priorityView.setText(priorityString);
*//*
        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
       */ // Get the appropriate background color based on the priority

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
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    public List<TaskEntry> getTasks() {
        return mTaskEntries;
    }


    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(String itemId);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id;
        TextView title;
        TextView moviePoster;
        TextView plot;
        TextView rating;
        TextView releastDate;
        TextView backdropPoster;



        public TaskViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.movieNameID);
         /*   updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            priorityView = itemView.findViewById(R.id.priorityTextView);*/
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String elementId = mTaskEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}