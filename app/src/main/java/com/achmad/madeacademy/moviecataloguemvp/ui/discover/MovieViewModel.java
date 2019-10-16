package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.NetworkRepository;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.movie.Movie;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.tvshow.TvShow;

import static com.achmad.madeacademy.moviecataloguemvp.utils.Constans.API_KEY;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<Movie> mutableLiveData;
    private MutableLiveData<TvShow> mutableTvShowData;
    private NetworkRepository networkRepository;



    public void init(){
        if (mutableLiveData != null){
            return;
        }
        networkRepository = NetworkRepository.getInstance();
        mutableLiveData = networkRepository.getMovie(API_KEY, "en-US");
    }
//    public void tvShowInit(){
//
//        if (mutableTvShowData != null){
//            return;
//        }
//        networkRepository = NetworkRepository.getInstance();
//        mutableTvShowData = networkRepository.getTvShow(API_KEY, "en-US");
//    }

    LiveData<Movie> getMovieRepository(){
        return mutableLiveData;
    }
    LiveData<TvShow> getTvShowRepository(){
        return mutableTvShowData;
    }

}
