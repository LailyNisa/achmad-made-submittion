package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.NetworkRepository;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Movie;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MutableLiveData<Movie> mutableLiveData;
    private MutableLiveData<Result> mutableLiveDataMovie;
    private LiveData<List<Result>> mutableMovieData;
    private LiveData<Integer> mutableErrorCode;
    private NetworkRepository networkRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        networkRepository = new NetworkRepository(application);
    }

    public void initPopular() {
        if (mutableLiveData != null) {
            return;
        }
        mutableLiveData = networkRepository.getMovie("popularity.desc");
    }

    public void initTopRated() {
        if (mutableLiveData != null) {
            return;
        }
        mutableLiveData = networkRepository.getMovie("vote_average.desc");
    }

    public void initDb() {
        if (mutableMovieData != null) {
            return;
        }
        mutableMovieData = networkRepository.getAllMovie();
    }

    LiveData<Movie> getMovieRepository() {
        return mutableLiveData;
    }

    LiveData<List<Result>> getMovieDb() {
        return mutableMovieData;
    }


    LiveData<Integer> getCodeErro() {
        mutableErrorCode = networkRepository.getErrorCode();
        return mutableErrorCode;
    }
}
