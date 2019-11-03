package com.achmad.madeacademy.moviecataloguemvp.data.remote;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Movie;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("movie")
    Call<Movie> getMovie();

    @GET("tv")
    Call<TvShow> getTvShow();
}
