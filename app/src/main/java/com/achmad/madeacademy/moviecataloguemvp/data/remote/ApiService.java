package com.achmad.madeacademy.moviecataloguemvp.data.remote;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Movie;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("discover/movie")
    Call<Movie> getMovie(@Query("sort_by") String sort);

    @GET("discover/tv")
    Call<TvShow> getTvShow(@Query("sort_by") String sort);

    @GET("search/movie")
    Call<Movie> getMovieSearch(@Query("query") String search);

    @GET("search/tv")
    Call<TvShow> getTvShowSearch(@Query("query") String search);

}
