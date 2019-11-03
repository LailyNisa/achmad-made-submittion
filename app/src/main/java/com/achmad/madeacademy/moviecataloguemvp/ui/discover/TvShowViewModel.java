package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.NetworkRepository;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.TvShow;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<TvShow> mutableTvShowData;

    public void init() {

        if (mutableTvShowData != null) {
            return;
        }
        NetworkRepository networkRepository = NetworkRepository.getInstance();
        mutableTvShowData = networkRepository.getTvShow();
    }

    LiveData<TvShow> getTvShowRepository() {
        return mutableTvShowData;
    }
}
