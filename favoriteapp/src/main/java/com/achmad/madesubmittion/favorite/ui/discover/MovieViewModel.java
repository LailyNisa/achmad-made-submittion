package com.achmad.madesubmittion.favorite.ui.discover;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.madesubmittion.favorite.data.remote.model.movie.Movie;
import com.achmad.madesubmittion.favorite.data.remote.model.movie.Result;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MutableLiveData<Movie> mutableLiveData;
    private MutableLiveData<List<Result>> mutableLiveDataMovie = new MutableLiveData<>();
    private LiveData<List<Result>> mutableMovieData;
    private LiveData<Integer> mutableErrorCode;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mutableMovieData = new MovieFragment().getMovieBack();
    }

    LiveData<Movie> getMovieRepository() {
        return mutableLiveData;
    }

    public void setMutableLiveDataMovie(List<Result> movie) {
        mutableLiveDataMovie.setValue(movie);
    }

    LiveData<List<Result>> getMovieDb() {
        return mutableLiveDataMovie;
    }
}
