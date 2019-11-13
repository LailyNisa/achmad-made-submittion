package com.achmad.madesubmittion.favorite.ui.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.madesubmittion.favorite.data.remote.model.movie.Result;

import java.util.List;

public class DiscoverDetailViewModel extends AndroidViewModel {
    private LiveData<List<Result>> mAllMovie;
    private LiveData<List<com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result>> mAllTvShow;
    private MutableLiveData<Integer> idLiveData = new MutableLiveData<>();
    private boolean isFavorite;

    public DiscoverDetailViewModel(@NonNull Application application) {
        super(application);
    }


    LiveData<List<com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result>> getTvShowRepository() {
        return mAllTvShow;
    }

    public void onFavoriteClicked() {
        isFavorite = !isFavorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
