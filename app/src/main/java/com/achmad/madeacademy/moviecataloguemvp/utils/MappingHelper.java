package com.achmad.madeacademy.moviecataloguemvp.utils;

import android.database.Cursor;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result> mapCursorToAlMovie(Cursor movie) {
        ArrayList<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result> movieList = new ArrayList<Result>();
        while (movie.moveToNext()) {
            int id = movie.getInt(movie.getColumnIndexOrThrow("id"));
            float popularity = movie.getFloat(movie.getColumnIndexOrThrow("popularity"));
            int voteCount = movie.getInt(movie.getColumnIndexOrThrow("voteCount"));
            boolean video = movie.getInt(movie.getColumnIndexOrThrow("video")) > 0;
            String poster_path = movie.getString(movie.getColumnIndexOrThrow("poster_path"));
            boolean adult = movie.getInt(movie.getColumnIndexOrThrow("adult")) > 0;
            String backdrop_path = movie.getString(movie.getColumnIndexOrThrow("backdrop_path"));
            String originalLanguage = movie.getString(movie.getColumnIndexOrThrow("originalLanguage"));
            String originalTitle = movie.getString(movie.getColumnIndexOrThrow("originalTitle"));
            String title = movie.getString(movie.getColumnIndexOrThrow("title"));
            float vote_average = movie.getFloat(movie.getColumnIndexOrThrow("vote_average"));
            String overview = movie.getString(movie.getColumnIndexOrThrow("overview"));
            String release_date = movie.getString(movie.getColumnIndexOrThrow("release_date"));
            movieList.add(new Result(popularity, voteCount, video, poster_path, id, adult, backdrop_path, originalLanguage, originalTitle, title, vote_average, overview, release_date));
        }
        movie.close();
        return movieList;
    }

    public static ArrayList<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result> mapCursorToAlTvShow(Cursor tvShow) {
        ArrayList<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result> tvShowList = new ArrayList<>();
        while (tvShow.moveToNext()) {
            String originalName = tvShow.getString(tvShow.getColumnIndexOrThrow("originalName"));
            String name = tvShow.getString(tvShow.getColumnIndexOrThrow("name"));
            float popularity = tvShow.getFloat(tvShow.getColumnIndexOrThrow("popularity"));
            int voteCount = tvShow.getInt(tvShow.getColumnIndexOrThrow("voteCount"));
            String firstAirDate = tvShow.getString(tvShow.getColumnIndexOrThrow("firstAirDate"));
            String backdropPath = tvShow.getString(tvShow.getColumnIndexOrThrow("backdropPath"));
            String originalLanguage = tvShow.getString(tvShow.getColumnIndexOrThrow("originalLanguage"));
            int id = tvShow.getInt(tvShow.getColumnIndexOrThrow("id"));
            float voteAverage = tvShow.getFloat(tvShow.getColumnIndexOrThrow("voteAverage"));
            String overview = tvShow.getString(tvShow.getColumnIndexOrThrow("overview"));
            String posterPath = tvShow.getString(tvShow.getColumnIndexOrThrow("posterPath"));
            tvShowList.add(new com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result(originalName, name, popularity, voteCount, firstAirDate, backdropPath, originalLanguage, id, voteAverage, overview, posterPath));
        }
        return tvShowList;
    }

}
