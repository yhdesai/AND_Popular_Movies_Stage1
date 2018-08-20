package io.github.yhdesai.PopularMovies.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import io.github.yhdesai.PopularMovies.data.AppDatabase;
import io.github.yhdesai.PopularMovies.data.BookmarkEntry;

public class AddBookmarkViewModel extends ViewModel {

    private LiveData<BookmarkEntry> bookmark;

   public AddBookmarkViewModel(AppDatabase database, String bookmarkId) {
        bookmark = database.bookmarkDao().loadBookmarkById(bookmarkId);
    }

    public LiveData<BookmarkEntry> getBookmark() {
        return bookmark;
    }
}
