package com.achmad.madeacademy.moviecataloguemvp.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result;

import java.util.List;

@Dao
public interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result movie);

    @Transaction
    @Query("SELECT * FROM tvshow WHERE tvshow.id= :tvshowId")
    LiveData<List<Result>> getTvShow(int tvshowId);

    @Transaction
    @Query("SELECT * FROM tvshow")
    LiveData<List<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result>> getAllTvShow();

    @Delete
    void delete(Result tvShow);
}