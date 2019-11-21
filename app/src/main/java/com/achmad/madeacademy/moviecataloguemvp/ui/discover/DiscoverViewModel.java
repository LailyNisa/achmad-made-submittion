package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.NetworkRepository;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Movie;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.TvShow;

import java.util.List;

public class DiscoverViewModel extends AndroidViewModel {
    private LiveData<Integer> mutableErrorCode;
    private NetworkRepository networkRepository;
    private MutableLiveData<Movie> mutableLiveDataMovieByDate;
    //Movie
    private MutableLiveData<Movie> mutableLiveData;
    private MutableLiveData<String> mutableMovieSearchText = new MutableLiveData<>();
    private LiveData<List<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result>> mutableMovieData;

    //TvShow
    private MutableLiveData<TvShow> mutableTvShowData;
    private MutableLiveData<String> mutableTvShowSearchText = new MutableLiveData<>();
    private LiveData<List<Result>> liveTvShowData;


    public DiscoverViewModel(@NonNull Application application) {
        super(application);
        networkRepository = new NetworkRepository(application);
    }

    /*
    Movie DataSource
     */
    void initMovieSearch() {
        mutableLiveData = networkRepository.getMovieSearch(getTextMovie().getValue());
    }

    void setTextMovieSearch(String textMovieSearch) {
        mutableMovieSearchText.setValue(textMovieSearch);
    }

    void initPopularMovie() {
        mutableLiveData = networkRepository.getMovie("popularity.desc");
    }

    void initTopRatedMovie() {
        mutableLiveData = networkRepository.getMovie("vote_average.desc");
    }

    void initDbMovie() {
        if (mutableMovieData != null) {
            return;
        }
        mutableMovieData = networkRepository.getAllMovie();
    }

    LiveData<String> getTextMovie() {
        return mutableMovieSearchText;
    }

    LiveData<Movie> getMovieRepository() {
        return mutableLiveData;
    }

    LiveData<List<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result>> getMovieDb() {
        return mutableMovieData;
    }

    /*
    TV Show DataSource
     */

    void initTvSearch() {
        mutableTvShowData = networkRepository.getTvShowSearch(getTextTvSearch().getValue());
    }

    LiveData<String> getTextTvSearch() {
        return mutableTvShowSearchText;
    }

    void setTextTvSearch(String textTvSearch) {
        mutableTvShowSearchText.setValue(textTvSearch);
    }

    void initPopularTvShow() {
        mutableTvShowData = networkRepository.getTvShow("popularity.desc");
    }

    void initTopRatedTvShow() {
        mutableTvShowData = networkRepository.getTvShow("vote_average.desc");
    }

    void initDbTvShow() {
        if (liveTvShowData != null) {
            return;
        }
        liveTvShowData = networkRepository.getAllTvShow();
    }

    LiveData<TvShow> getTvShowRepository() {
        return mutableTvShowData;
    }

    LiveData<List<Result>> getTvShowDb() {
        return liveTvShowData;
    }

    /*
    Discover Activity DataSource
     */
    void initMovieReleaseToday(String date) {
        mutableLiveDataMovieByDate = networkRepository.getMovieSearchByDate(date);
    }

    LiveData<Movie> getMovieRelease() {
        return mutableLiveDataMovieByDate;
    }


    /*
    Both Movie and TvShow
     */

    LiveData<Integer> getCodeErro() {
        mutableErrorCode = networkRepository.getErrorCode();
        return mutableErrorCode;
    }


}
