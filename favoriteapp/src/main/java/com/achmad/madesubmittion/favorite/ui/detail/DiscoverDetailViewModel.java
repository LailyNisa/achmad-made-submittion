package com.achmad.madesubmittion.favorite.ui.detail;

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.madesubmittion.favorite.data.remote.model.movie.Result;
import com.achmad.madesubmittion.favorite.utils.MappingHelper;

import java.util.List;

import static com.achmad.madesubmittion.favorite.data.local.DiscoverContract.MOVIE_URI;
import static com.achmad.madesubmittion.favorite.data.local.DiscoverContract.TVSHOW_URI;

public class DiscoverDetailViewModel extends AndroidViewModel {
    private LiveData<Result> mAllMovie;
    private LiveData<List<com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result>> mAllTvShow;
    private MutableLiveData<Integer> idLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> movieDataResultLive = new MutableLiveData<>();
    private MutableLiveData<com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result> tvshowDataResultLive = new MutableLiveData<>();
    private boolean isFavorite;
    private int idMovie;
    private Uri uriWithId;
    private Result movie;
    private Application application;
    public DiscoverDetailViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void initDbMovieId(int id) {
//        if (movieDataResultLive.getValue() != null) {
//            return;
//        }
        setIdMovie(id);
        if (getIdMovie() == 0) {
            getMovieDb(0);
        }
        getMovieDb(getIdMovie());
        setIdLiveData(id);
    }

    public void initDbTvShowId(int id) {
//        if (tvshowDataResultLive.getValue() != null) {
//            return;
//        }
        setIdMovie(id);
        if (getIdMovie() == 0) {
            getTvShowDb(0);
        }
        getTvShowDb(getIdMovie());
        setIdLiveData(id);
    }

    public LiveData<Result> getMovieDb(int id) {
        uriWithId = Uri.parse(MOVIE_URI + "/" + id);
        Cursor cursor = application.getContentResolver().query(uriWithId, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            movieDataResultLive.postValue(MappingHelper.mapMovieCursorToObject(cursor));
            cursor.close();
        } else {
            movieDataResultLive.postValue(null);
        }
        return movieDataResultLive;
    }

    public LiveData<com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result> getTvShowDb(int id) {
        uriWithId = Uri.parse(TVSHOW_URI + "/" + id);
        Cursor cursor = application.getContentResolver().query(uriWithId, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            tvshowDataResultLive.postValue(MappingHelper.mapTvShowCursorToObject(cursor));
            cursor.close();
        } else {
            tvshowDataResultLive.postValue(null);
        }
        return tvshowDataResultLive;
    }

    LiveData<Result> getMovieRepository() {
        return movieDataResultLive;
    }


    LiveData<com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result> getTvShowRepository() {
        return tvshowDataResultLive;
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

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public void setIdLiveData(int id) {
        idLiveData.setValue(id);
    }

    public LiveData<Integer> integerLiveData() {
        return idLiveData;
    }


    public void setMovieResult(Result movie) {
        movieDataResultLive.setValue(movie);
    }
}
