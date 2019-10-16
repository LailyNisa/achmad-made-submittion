package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.NetworkRepository;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.tvshow.TvShow;

import static com.achmad.madeacademy.moviecataloguemvp.utils.Constans.API_KEY;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<TvShow> mutableTvShowData;

    public void init(){

        if (mutableTvShowData != null){
            return;
        }
        NetworkRepository networkRepository = NetworkRepository.getInstance();
        mutableTvShowData = networkRepository.getTvShow(API_KEY, "en-US");
    }
    LiveData<TvShow> getTvShowRepository(){
        return mutableTvShowData;
    }

}
