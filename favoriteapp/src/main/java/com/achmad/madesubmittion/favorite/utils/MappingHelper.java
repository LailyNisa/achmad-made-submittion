package com.achmad.madesubmittion.favorite.utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.achmad.madesubmittion.favorite.data.local.DiscoverContract;
import com.achmad.madesubmittion.favorite.data.remote.model.movie.Result;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<com.achmad.madesubmittion.favorite.data.remote.model.movie.Result> mapCursorToAlMovie(Cursor movie) {
        ArrayList<com.achmad.madesubmittion.favorite.data.remote.model.movie.Result> movieList = new ArrayList<>();
        if (movie != null) {
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
        } else {
            movieList.add(new Result());
        }

        return movieList;
    }

    public static ArrayList<com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result> mapCursorToAlTvShow(Cursor tvShow) {
        ArrayList<com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result> tvShowList = new ArrayList<>();
        if (tvShow != null) {
            while (tvShow.moveToNext()) {
                String originalName = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.ORIGINAL_NAME));
                String name = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.NAME));
                float popularity = tvShow.getFloat(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.POPULARITY));
                int voteCount = tvShow.getInt(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.VOTE_COUNT));
                String firstAirDate = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.FIRSTAIRDATE));
                String backdropPath = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.BACKDROPPATH));
                String originalLanguage = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.ORIGINAL_LANGUAGE));
                int id = tvShow.getInt(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.ID));
                float voteAverage = tvShow.getFloat(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.VOTE_AVERAGE));
                String overview = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.OVERVIEW));
                String posterPath = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.POSTERPATH));
                tvShowList.add(new com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result(originalName, name, popularity, voteCount, firstAirDate, backdropPath, originalLanguage, id, voteAverage, overview, posterPath));
            }
            tvShow.close();
        } else {
            tvShowList.add(new com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result());
        }
        return tvShowList;
    }

    public static Result mapMovieCursorToObject(Cursor movie) {
        if (movie != null) {
            movie.moveToFirst();
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
            return new Result(popularity, voteCount, video, poster_path, id, adult, backdrop_path, originalLanguage, originalTitle, title, vote_average, overview, release_date);
        } else {
            return new Result();
        }
    }

    public static com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result mapTvShowCursorToObject(Cursor tvShow) {
        if (tvShow != null) {
            tvShow.moveToFirst();
            String originalName = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.ORIGINAL_NAME));
            String name = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.NAME));
            float popularity = tvShow.getFloat(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.POPULARITY));
            int voteCount = tvShow.getInt(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.VOTE_COUNT));
            String firstAirDate = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.FIRSTAIRDATE));
            String backdropPath = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.BACKDROPPATH));
            String originalLanguage = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.ORIGINAL_LANGUAGE));
            int id = tvShow.getInt(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.ID));
            float voteAverage = tvShow.getFloat(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.VOTE_AVERAGE));
            String overview = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.OVERVIEW));
            String posterPath = tvShow.getString(tvShow.getColumnIndexOrThrow(DiscoverContract.TvShowColumns.POSTERPATH));
            return new com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result(originalName, name, popularity, voteCount, firstAirDate, backdropPath, originalLanguage, id, voteAverage, overview, posterPath);
        } else {
            return new com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result();
        }
    }

    public static Result fromContentValues(ContentValues values) {
        final Result movie = new Result();
        if (values.containsKey(DiscoverContract.MovieColumns.ID)) {
            movie.id = values.getAsInteger(DiscoverContract.MovieColumns.ID);
        }
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
}
