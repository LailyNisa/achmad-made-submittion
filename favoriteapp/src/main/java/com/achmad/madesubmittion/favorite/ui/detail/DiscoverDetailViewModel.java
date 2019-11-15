package com.achmad.madesubmittion.favorite.ui.detail;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.madesubmittion.favorite.data.remote.model.movie.Result;

import java.util.List;

public class DiscoverDetailViewModel extends AndroidViewModel {
    private LiveData<Result> mAllMovie;
    private LiveData<List<com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result>> mAllTvShow;
    private MutableLiveData<Integer> idLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> movieDataResultLive = new MutableLiveData<>();
    private boolean isFavorite;
    private Uri uriWithId;
    private Result movie;
    public DiscoverDetailViewModel(@NonNull Application application) {
        super(application);
    }

    //    public void initDbMovieId(int id) {
//        if (mAllMovie != null) {
//            return;
//        }
//        mAllMovie = Transformations.switchMap(idLiveData,
//                setMovieResult();
//        setIdLiveData(id);
//    }
    LiveData<Result> getMovieRepository() {
        return mAllMovie;
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

    private void setIdLiveData(int id) {
        idLiveData.setValue(id);
    }

    public void setMovieResult(Result movie) {
        movieDataResultLive.setValue(movie);
    }
}
