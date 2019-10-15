package com.achmad.madeacademy.moviecataloguemvp.data.source.remote;

import androidx.lifecycle.MutableLiveData;

import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NetworkRepository {
    private static NetworkRepository networkRepository;

    public static NetworkRepository getInstance(){
        if (networkRepository ==null){
            networkRepository = new NetworkRepository();
        }
        return networkRepository;
    }

    private ApiService movieApi;

    public NetworkRepository(){
        movieApi = NetworkConfig.createService(ApiService.class);
    }

    public MutableLiveData<Movie> getMovie(String apiKey,String language){
        final MutableLiveData<Movie> movieData = new MutableLiveData<>();
        movieApi.getMovie(apiKey,language).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()){
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
}
