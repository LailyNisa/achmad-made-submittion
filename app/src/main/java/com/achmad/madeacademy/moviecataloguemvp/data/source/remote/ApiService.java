package com.achmad.madeacademy.moviecataloguemvp.data.source.remote;

import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie")
    Call<Movie> getMovie(@Query("api_key")String apiKey,@Query("language") String language );
}

