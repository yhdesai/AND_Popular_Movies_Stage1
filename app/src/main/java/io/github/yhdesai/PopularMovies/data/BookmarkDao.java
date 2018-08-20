package io.github.yhdesai.PopularMovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

@Dao
public interface BookmarkDao {

    @Query("SELECT * FROM movie ORDER BY rating")
    LiveData<List<BookmarkEntry>> loadAllBookmarks();

    @Insert
    void insertBookmark(BookmarkEntry bookmarkEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateBookmark(BookmarkEntry taskEntry);

    @Delete
    void deleteBookmark(BookmarkEntry bookmarkEntry);

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<BookmarkEntry> loadBookmarkById(String id);
}
