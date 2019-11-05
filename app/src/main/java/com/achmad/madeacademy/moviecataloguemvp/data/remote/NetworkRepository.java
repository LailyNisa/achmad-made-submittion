package com.achmad.madeacademy.moviecataloguemvp.data.remote;

import androidx.lifecycle.MutableLiveData;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Movie;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.TvShow;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkRepository {
    private static NetworkRepository networkRepository;

    public static NetworkRepository getInstance() {
        if (networkRepository == null) {
            networkRepository = new NetworkRepository();
        }
        return networkRepository;
    }

    private ApiService movieApi;

    private NetworkRepository() {
        movieApi = NetworkConfig.getInstance();
    }

    public MutableLiveData<Movie> getMovie(String sort) {
        final MutableLiveData<Movie> movieData = new MutableLiveData<>();
        movieApi.getMovie(sort).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }


    public MutableLiveData<TvShow> getTvShow(String sort) {
        final MutableLiveData<TvShow> tvShowMutableLiveData = new MutableLiveData<>();
        movieApi.getTvShow(sort).enqueue(new Callback<TvShow>() {

            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                if (response.isSuccessful()) {
                    tvShowMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<TvShow> call, @NotNull Throwable t) {
                tvShowMutableLiveData.setValue(null);
            }
        });
        return tvShowMutableLiveData;
    }
}

