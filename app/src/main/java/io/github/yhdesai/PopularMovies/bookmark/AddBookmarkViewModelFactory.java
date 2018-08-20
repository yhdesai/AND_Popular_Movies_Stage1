package io.github.yhdesai.PopularMovies.bookmark;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;


import io.github.yhdesai.PopularMovies.data.AppDatabase;
import io.github.yhdesai.PopularMovies.model.AddBookmarkViewModel;

public class AddBookmarkViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final String mTaskId;

   public AddBookmarkViewModelFactory(AppDatabase database, String taskId) {
        mDb = database;
        mTaskId = taskId;
    }

  @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddBookmarkViewModel(mDb, mTaskId);
    }
}
