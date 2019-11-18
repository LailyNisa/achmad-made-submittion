package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.NetworkRepository;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.TvShow;

import java.util.List;

public class TvShowViewModel extends AndroidViewModel {
    private MutableLiveData<TvShow> mutableTvShowData;
    private NetworkRepository networkRepository;
    private LiveData<List<Result>> liveShowData;

    public TvShowViewModel(@NonNull Application application) {
        super(application);
        networkRepository = new NetworkRepository(application);
    }

    void initTvSearch(String search) {
        mutableTvShowData = networkRepository.getTvShowSearch(search);
    }

    void initPopular() {
        mutableTvShowData = networkRepository.getTvShow("popularity.desc");
    }

    void initTopRated() {
        mutableTvShowData = networkRepository.getTvShow("vote_average.desc");
    }

    void initDb() {
        if (liveShowData != null) {
            return;
        }
        liveShowData = networkRepository.getAllTvShow();
    }

    LiveData<TvShow> getTvShowRepository() {
        return mutableTvShowData;
    }

    LiveData<List<Result>> getTvShowDb() {
        return liveShowData;
    }
}
