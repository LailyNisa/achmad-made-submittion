package com.achmad.selflearning.mydiscoverconsumer.ui.discover;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.selflearning.mydiscoverconsumer.data.remote.NetworkRepository;
import com.achmad.selflearning.mydiscoverconsumer.data.remote.model.movie.Movie;
import com.achmad.selflearning.mydiscoverconsumer.data.remote.model.movie.Result;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MutableLiveData<Movie> mutableLiveData;
    private LiveData<List<Result>> mutableMovieData;
    private LiveData<Integer> mutableErrorCode;
    private NetworkRepository networkRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        networkRepository = new NetworkRepository(application);
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