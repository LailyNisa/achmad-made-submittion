package com.achmad.madeacademy.moviecataloguemvp.data.local;

import android.net.Uri;
import android.provider.BaseColumns;

public class DiscoverContract {
    public static final String AUTHORITY = "com.achmad.madeacademy.moviecataloguemvp";
    public static final String DISCOVER_DATABASE = "discover_database";
    public static final String MOVIE_ID_COLUMN = "id";
    private static final String SCHEME = "content";
    public static final Uri MOVIE_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(MovieColumns.MOVIE_TABLE)
            .build();
    public static final Uri TVSHOW_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TvShowColumns.TVSHOW_TABLE)
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

    public static final class TvShowColumns implements BaseColumns {
        public static final String TVSHOW_TABLE = "tvshow";
        public static final String ORIGINAL_NAME = "originalName";
        public static final String NAME = "name";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "voteCount";
        public static final String FIRSTAIRDATE = "first_air_date";
        public static final String BACKDROPPATH = "backdrop_path";
        public static final String ORIGINAL_LANGUAGE = "originalLanguage";
        public static final String ID = "id";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String OVERVIEW = "overview";
        public static final String POSTERPATH = "poster_path";
    }
}
