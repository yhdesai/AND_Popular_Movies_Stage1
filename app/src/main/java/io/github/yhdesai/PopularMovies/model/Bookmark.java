
package io.github.yhdesai.PopularMovies.model;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;


import java.util.List;

import io.github.yhdesai.PopularMovies.R;
import io.github.yhdesai.PopularMovies.adapter.BookmarkAdapter;
import io.github.yhdesai.PopularMovies.bookmark.AppExecutors;
import io.github.yhdesai.PopularMovies.data.AppDatabase;
import io.github.yhdesai.PopularMovies.data.BookmarkEntry;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


public class Bookmark extends AppCompatActivity implements BookmarkAdapter.ItemClickListener {

    private static final String TAG = Bookmark.class.getSimpleName();
    RecyclerView mRecyclerView;
    private BookmarkAdapter mAdapter;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        mRecyclerView = findViewById(R.id.recyclerViewTasks);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

       mAdapter = new BookmarkAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

           @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<BookmarkEntry> bookmarks = mAdapter.getBookmarks();
                        mDb.bookmarkDao().deleteBookmark(bookmarks.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);


        mDb = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getBookmarks().observe(this, new Observer<List<BookmarkEntry>>() {
            @Override
            public void onChanged(@Nullable List<BookmarkEntry> bookmarkEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.setBookmarks(bookmarkEntries);
            }
        });
    }

    @Override
    public void onItemClickListener(String itemId) {
       Log.d("ItemId", itemId);
        /*Intent intent = new Intent(Bookmark.this, DetailActivity.class);
        intent.putExtra(AddBookmarkActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);*/
    }


}
