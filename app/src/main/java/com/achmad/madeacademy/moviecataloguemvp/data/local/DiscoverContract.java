package com.achmad.madeacademy.moviecataloguemvp.data.local;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;

public class DiscoverContract {
    public static final String AUTHORITY = "com.achmad.madeacademy.moviecataloguemvp";

    public static final String TVSHOW_TABLE = "tvshow";
    public static final String DISCOVER_DATABASE = "discover_database";
    public static final String MOVIE_ID_COLUMN = "id";
    private static final String SCHEME = "content";
    public static final Uri MOVIE_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(MovieColumns.MOVIE_TABLE)
            .build();
    public static final Uri TVSHOW_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TVSHOW_TABLE)
            .build();

    public DiscoverContract() {
    }

    public static final class MovieColumns implements BaseColumns {
        public static final String MOVIE_TABLE = "movie";
        public static final String ID = "id";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "voteCount";
        public static final String VIDEO = "video";
        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String ORIGINAL_LANGUAGE = "originalLanguage";
        public static final String ORIGINAL_TITLE = "originalTitle";
        public static final String TITLE = "title";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
    }

    public static Result movieFromContentValues(ContentValues values) {
        final Result movie = new Result();
        if (values.containsKey(MOVIE_ID_COLUMN)) {
            movie.id = values.getAsInteger(MOVIE_ID_COLUMN);
        }

        return movie;
    }

    public static com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvShowFromContentValues(ContentValues values) {
        final com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvshow = new com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result();
        if (values.containsKey(MOVIE_ID_COLUMN)) {
            tvshow.id = values.getAsInteger(MOVIE_ID_COLUMN);
        }

        return tvshow;
    }
}
