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

public class DiscoverDetailViewModel extends AndroidViewModel {
    private LiveData<Result> mAllMovie;
    private LiveData<List<com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result>> mAllTvShow;
    private MutableLiveData<Integer> idLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> movieDataResultLive = new MutableLiveData<>();
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

//        mAllMovie = Transformations.switchMap(idLiveData, this::getMovieDb);
        setIdMovie(id);
        if (getIdMovie() == 0) {
            getMovieDb(0);
        }
        getMovieDb(getIdMovie());

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

//            mAllMovie = movieDataResultLive;
        return movieDataResultLive;
    }

    public void setmAllMovie(Result movie) {
        movieDataResultLive.setValue(movie);
    }
    LiveData<Result> getMovieRepository() {
        return movieDataResultLive;
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
