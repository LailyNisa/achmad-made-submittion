package com.achmad.selflearning.mydiscoverconsumer.ui.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.achmad.selflearning.mydiscoverconsumer.data.remote.NetworkRepository;
import com.achmad.selflearning.mydiscoverconsumer.data.remote.model.movie.Result;

import java.util.List;

public class DiscoverDetailViewModel extends AndroidViewModel {
    private NetworkRepository mRepository;
    private LiveData<List<Result>> mAllMovie;
    private LiveData<List<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result>> mAllTvShow;
    private MutableLiveData<Integer> idLiveData = new MutableLiveData<>();
    private boolean isFavorite;

    public DiscoverDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NetworkRepository(application);
    }

    public void initDbMovieId(int id) {
        if (mAllMovie != null) {
            return;
        }
        mAllMovie = Transformations.switchMap(idLiveData,
                input -> mRepository.getMovieDb(input));
        setIdLiveData(id);
    }

    public void initTvShow(int id) {
        if (mAllTvShow != null) {
            return;
        }
        mAllTvShow = Transformations.switchMap(idLiveData,
                input -> mRepository.getTvShowDb(input));
        setIdLiveData(id);
    }

    private void setIdLiveData(int id) {
        idLiveData.setValue(id);
    }

    public void insertMovie(Result movie) {
        mRepository.insertMovie(movie);
    }

    public void insertTvShow(com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvshow) {
        mRepository.insertTvShow(tvshow);
    }

    LiveData<List<Result>> getMovieRepository() {
        return mAllMovie;
    }

    LiveData<List<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result>> getTvShowRepository() {
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

    public void deleteMovie(Result movie) {
        mRepository.deleteDbMovie(movie);
    }

    public void deleteTvshow(com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvshow) {
        mRepository.deleteDbTvShow(tvshow);
    }
}
