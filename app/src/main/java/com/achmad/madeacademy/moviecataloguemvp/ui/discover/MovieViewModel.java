package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.NetworkRepository;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Movie;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<Movie> mutableLiveData;
    private NetworkRepository networkRepository;

    public void initPopular() {
        if (mutableLiveData != null) {
            return;
        }
        networkRepository = NetworkRepository.getInstance();
        mutableLiveData = networkRepository.getMovie("popularity.desc");
    }

    public void initTopRated() {
        if (mutableLiveData != null) {
            return;
        }
        networkRepository = NetworkRepository.getInstance();
        mutableLiveData = networkRepository.getMovie("vote_average.desc");
    }

    LiveData<Movie> getMovieRepository() {
        return mutableLiveData;
    }
}
