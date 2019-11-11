package com.achmad.selflearning.mydiscoverconsumer.data.local.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result;

import java.util.List;

@Dao
public interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Result movie);

    @Insert
    long insertTvShowProvider(Result movie);

    @Transaction
    @Query("SELECT * FROM tvshow WHERE tvshow.id= :tvshowId")
    LiveData<List<Result>> getTvShow(int tvshowId);

    @Transaction
    @Query("SELECT * FROM tvshow WHERE tvshow.id= :movieId")
    Cursor selectTvhow(int movieId);

    @Transaction
    @Query("SELECT * FROM tvshow")
    LiveData<List<Result>> getAllTvShow();

    @Transaction
    @Query("SELECT * FROM tvshow")
    Cursor getAllTvShowCursor();

    @Query("DELETE FROM tvshow WHERE tvshow.id= :movieId ")
    int deleteId(int movieId);

    @Delete
    void delete(Result tvShow);
}
