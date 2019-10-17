package com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model;

import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.movie.Movie;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.tvshow.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie")
    Call<Movie> getMovie(@Query("api_key")String apiKey, @Query("language") String language );

    @GET("tv")
    Call<TvShow> getTvShow(@Query("api_key")String apiKey, @Query("language") String language );
}
