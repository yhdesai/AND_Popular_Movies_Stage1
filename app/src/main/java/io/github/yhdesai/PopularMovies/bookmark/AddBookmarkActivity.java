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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


import io.github.yhdesai.PopularMovies.R;
import io.github.yhdesai.PopularMovies.data.AppDatabase;
import io.github.yhdesai.PopularMovies.data.BookmarkEntry;
import io.github.yhdesai.PopularMovies.model.AddBookmarkViewModel;


public class AddBookmarkActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";

    private static final String DEFAULT_TASK_ID = "-1";
    private static final String TAG = AddBookmarkActivity.class.getSimpleName();
   EditText mEditText;
    RadioGroup mRadioGroup;
    Button mButton;

    private String mTaskId = DEFAULT_TASK_ID;

   private AppDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getString(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText(R.string.update_button);
            if (mTaskId == DEFAULT_TASK_ID) {
                mTaskId = intent.getStringExtra(EXTRA_TASK_ID);


                AddBookmarkViewModelFactory factory = new AddBookmarkViewModelFactory(mDb, mTaskId);
                final AddBookmarkViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddBookmarkViewModel.class);

                viewModel.getBookmark().observe(this, new Observer<BookmarkEntry>() {
                    @Override
                    public void onChanged(@Nullable BookmarkEntry bookmarkEntry) {
                        viewModel.getBookmark().removeObserver(this);
                        populateUI(bookmarkEntry);
                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }


    private void initViews() {
        mEditText = findViewById(R.id.editTextTaskDescription);
        mRadioGroup = findViewById(R.id.radioGroup);

        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }


    private void populateUI(BookmarkEntry bookmark) {
        if (bookmark == null) {
            return;
        }
        mEditText.setText(bookmark.getTitle());
    }


    public void onSaveButtonClicked() {
        String id = "1";
        String title = "title";
        String moviePoster = "test";
        String plot = "plot";
        String rating = "rating";
        String releaseDate = "date";
        String backdropPoster = "poster";


        final BookmarkEntry bookmark = new BookmarkEntry(id, title, moviePoster, plot, rating, releaseDate, backdropPoster);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.bookmarkDao().insertBookmark(bookmark);
                } else {
                       //update task
                    bookmark.setId(mTaskId);
                    mDb.bookmarkDao().updateBookmark(bookmark);
                }
                finish();
            }
        });
    }


}
