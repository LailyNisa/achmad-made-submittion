package com.achmad.madeacademy.moviecataloguemvp.utils;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverContract;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;

import java.util.ArrayList;
import java.util.List;

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

    public static LiveData<List<Result>> mapCursorToAlMovieLiveData(Cursor movie) {
        MutableLiveData<List<Result>> movieListLive = new MutableLiveData<>();
        ArrayList<Result> movieList = new ArrayList<>();
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
            movieListLive.setValue(movieList);
        }
        movie.close();
        return movieListLive;
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

    public static Result fromContentValues(ContentValues values) {
        final Result movie = new Result();
        if (values.containsKey(DiscoverContract.MovieColumns.POPULARITY)) {
            movie.popularity = values.getAsInteger(DiscoverContract.MovieColumns.POPULARITY);
        }
        if (values.containsKey(DiscoverContract.MovieColumns.VOTE_COUNT)) {
            movie.voteCount = values.getAsInteger(DiscoverContract.MovieColumns.VOTE_COUNT);
        }
        if (values.containsKey(DiscoverContract.MovieColumns.VIDEO)) {
            movie.video = values.getAsInteger(DiscoverContract.MovieColumns.VIDEO) > 0;
        }
        if (values.containsKey(DiscoverContract.MovieColumns.POSTER_PATH)) {
            movie.posterPath = values.getAsString(DiscoverContract.MovieColumns.POSTER_PATH);
        }
        if (values.containsKey(DiscoverContract.MovieColumns.ID)) {
            movie.id = values.getAsInteger(DiscoverContract.MovieColumns.ID);
        }
        if (values.containsKey(DiscoverContract.MovieColumns.ADULT)) {
            movie.adult = values.getAsInteger(DiscoverContract.MovieColumns.ADULT) > 0;
        }
        if (values.containsKey(DiscoverContract.MovieColumns.BACKDROP_PATH)) {
            movie.backdropPath = values.getAsString(DiscoverContract.MovieColumns.BACKDROP_PATH);
        }
        if (values.containsKey(DiscoverContract.MovieColumns.ORIGINAL_LANGUAGE)) {
            movie.originalLanguage = values.getAsString(DiscoverContract.MovieColumns.ORIGINAL_LANGUAGE);
        }
        if (values.containsKey(DiscoverContract.MovieColumns.ORIGINAL_TITLE)) {
            movie.originalTitle = values.getAsString(DiscoverContract.MovieColumns.ORIGINAL_TITLE);
        }
        if (values.containsKey(DiscoverContract.MovieColumns.TITLE)) {
            movie.title = values.getAsString(DiscoverContract.MovieColumns.TITLE);
        }
        if (values.containsKey(DiscoverContract.MovieColumns.VOTE_AVERAGE)) {
            movie.voteAverage = values.getAsFloat(DiscoverContract.MovieColumns.VOTE_AVERAGE);
        }
        if (values.containsKey(DiscoverContract.MovieColumns.OVERVIEW)) {
            movie.overview = values.getAsString(DiscoverContract.MovieColumns.OVERVIEW);
        }
        if (values.containsKey(DiscoverContract.MovieColumns.RELEASE_DATE)) {
            movie.releaseDate = values.getAsString(DiscoverContract.MovieColumns.RELEASE_DATE);
        }
        return movie;
    }

    public static com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvShowfromContentValues(ContentValues values) {
        final com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvshow = new com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result();
        if (values.containsKey(DiscoverContract.TvShowColumns.ORIGINAL_NAME)) {
            tvshow.originalName = values.getAsString(DiscoverContract.TvShowColumns.ORIGINAL_NAME);
        }
        if (values.containsKey(DiscoverContract.TvShowColumns.POPULARITY)) {
            tvshow.popularity = values.getAsInteger(DiscoverContract.TvShowColumns.POPULARITY);
        }
        if (values.containsKey(DiscoverContract.TvShowColumns.VOTE_COUNT)) {
            tvshow.voteCount = values.getAsInteger(DiscoverContract.TvShowColumns.VOTE_COUNT);
        }
        if (values.containsKey(DiscoverContract.TvShowColumns.POSTERPATH)) {
            tvshow.posterPath = values.getAsString(DiscoverContract.TvShowColumns.POSTERPATH);
        }
        if (values.containsKey(DiscoverContract.TvShowColumns.ID)) {
            tvshow.id = values.getAsInteger(DiscoverContract.TvShowColumns.ID);
        }
        if (values.containsKey(DiscoverContract.TvShowColumns.BACKDROPPATH)) {
            tvshow.backdropPath = values.getAsString(DiscoverContract.TvShowColumns.BACKDROPPATH);
        }
        if (values.containsKey(DiscoverContract.TvShowColumns.ORIGINAL_LANGUAGE)) {
            tvshow.originalLanguage = values.getAsString(DiscoverContract.TvShowColumns.ORIGINAL_LANGUAGE);
        }
        if (values.containsKey(DiscoverContract.TvShowColumns.FIRSTAIRDATE)) {
            tvshow.firstAirDate = values.getAsString(DiscoverContract.TvShowColumns.FIRSTAIRDATE);
        }
        if (values.containsKey(DiscoverContract.TvShowColumns.VOTE_AVERAGE)) {
            tvshow.voteAverage = values.getAsFloat(DiscoverContract.TvShowColumns.VOTE_AVERAGE);
        }
        if (values.containsKey(DiscoverContract.TvShowColumns.OVERVIEW)) {
            tvshow.overview = values.getAsString(DiscoverContract.TvShowColumns.OVERVIEW);
        }
        if (values.containsKey(DiscoverContract.TvShowColumns.NAME)) {
            tvshow.name = values.getAsString(DiscoverContract.TvShowColumns.NAME);
        }
        return tvshow;
    }

}
