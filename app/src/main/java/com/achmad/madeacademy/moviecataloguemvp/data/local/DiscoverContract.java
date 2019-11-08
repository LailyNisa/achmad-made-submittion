package com.achmad.madeacademy.moviecataloguemvp.data.local;

import android.net.Uri;

public class DiscoverContract {
    public static final String AUTHORITY = "com.achmad.madeacademy.moviecataloguemvp";
    public static final String MOVIE_TABLE = "movie";
    public static final String TVSHOW_TABLE = "tvshow";
    public static final String DISCOVER_DATABASE = "discover_database";
    private static final String SCHEME = "content";
    public static final Uri MOVIE_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(MOVIE_TABLE)
            .build();
    public static final Uri TVSHOW_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TVSHOW_TABLE)
            .build();

    public DiscoverContract() {
    }
}
