package com.achmad.madeacademy.moviecataloguemvp.data.local.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Result movie);

    @Insert
    long insertMovieProvider(Result movie);

    @Transaction
    @Query("SELECT * FROM movie WHERE movie.id= :movieId")
    LiveData<List<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result>> getMovie(int movieId);

    @Transaction
    @Query("SELECT * FROM movie WHERE movie.id= :movieId")
    Cursor selectMovie(int movieId);

    @Transaction
    @Query("SELECT * FROM movie")
    LiveData<List<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result>> getAllMovie();

    @Transaction
    @Query("SELECT * FROM movie")
    Cursor getAllMovieCursor();

    @Query("DELETE FROM movie WHERE movie.id= :movieId ")
    int deleteId(int movieId);

    @Delete
    void delete(Result movie);
}
