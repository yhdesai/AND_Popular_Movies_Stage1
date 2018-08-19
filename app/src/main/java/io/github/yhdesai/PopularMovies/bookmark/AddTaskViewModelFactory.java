package io.github.yhdesai.PopularMovies.bookmark;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;


import io.github.yhdesai.PopularMovies.data.AppDatabase;

// COMPLETED (1) Make this class extend ViewModel ViewModelProvider.NewInstanceFactory
public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // COMPLETED (2) Add two member variables. One for the database and one for the taskId
    private final AppDatabase mDb;
    private final String mTaskId;

    // COMPLETED (3) Initialize the member variables in the constructor with the parameters received
    public AddTaskViewModelFactory(AppDatabase database, String taskId) {
        mDb = database;
        mTaskId = taskId;
    }

    // COMPLETED (4) Uncomment the following method
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddTaskViewModel(mDb, mTaskId);
    }
}
