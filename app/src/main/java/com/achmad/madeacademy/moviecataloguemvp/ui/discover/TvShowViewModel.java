package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.NetworkRepository;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.TvShow;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<TvShow> mutableTvShowData;
    private NetworkRepository networkRepository;

    public void initPopular() {
        if (mutableTvShowData != null) {
            return;
        }
        networkRepository = NetworkRepository.getInstance();
        mutableTvShowData = networkRepository.getTvShow("popularity.desc");
    }

    public void initTopRated() {
        if (mutableTvShowData != null) {
            return;
        }
        networkRepository = NetworkRepository.getInstance();
        mutableTvShowData = networkRepository.getTvShow("vote_average.desc");
    }

    LiveData<TvShow> getTvShowRepository() {
        return mutableTvShowData;
    }
}
