package com.achmad.madesubmittion.favorite.ui.discover;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result;
import com.achmad.madesubmittion.favorite.data.remote.model.tvshow.TvShow;

import java.util.List;

public class TvShowViewModel extends AndroidViewModel {
    private MutableLiveData<TvShow> mutableTvShowData;
    private LiveData<List<Result>> liveShowData;

    public TvShowViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<TvShow> getTvShowRepository() {
        return mutableTvShowData;
    }

    LiveData<List<Result>> getTvShowDb() {
        return liveShowData;
    }
}
