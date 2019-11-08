package com.achmad.madeacademy.moviecataloguemvp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;

import com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverDatabase;
import com.achmad.madeacademy.moviecataloguemvp.data.local.dao.MovieDao;
import com.achmad.madeacademy.moviecataloguemvp.data.local.dao.TvShowDao;

import java.util.Objects;

import static com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverContract.AUTHORITY;
import static com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverContract.DISCOVER_DATABASE;
import static com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverContract.MOVIE_TABLE;
import static com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverContract.TVSHOW_TABLE;

public class MyContentProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TVSHOW = 3;
    private static final int TVSHOW_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, MOVIE_TABLE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, MOVIE_TABLE + "/#", MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY, TVSHOW_TABLE, TVSHOW);
        sUriMatcher.addURI(AUTHORITY, TVSHOW_TABLE + "/#", TVSHOW_ID);
    }

    private DiscoverDatabase appDatabase;
    private MovieDao movieDao;
    private TvShowDao tvShowDao;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        appDatabase = Room.databaseBuilder(getContext(), DiscoverDatabase.class, DISCOVER_DATABASE).build();
        movieDao = appDatabase.movieDao();
        tvShowDao = appDatabase.tvShowDao();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieDao.getAllMovieCursor();
                break;
            case MOVIE_ID:
                cursor = movieDao.selectMovie(Integer.parseInt(Objects.requireNonNull(uri.getLastPathSegment())));
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
