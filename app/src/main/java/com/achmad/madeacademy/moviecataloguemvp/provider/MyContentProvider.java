package com.achmad.madeacademy.moviecataloguemvp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverDatabase;
import com.achmad.madeacademy.moviecataloguemvp.utils.MappingHelper;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverContract.AUTHORITY;
import static com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverContract.MovieColumns.MOVIE_TABLE;
import static com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverContract.TvShowColumns.TVSHOW_TABLE;

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

    @Override
    public int delete(@NotNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        final int count;
        final Context context;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
            case TVSHOW:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case MOVIE_ID:
                context = getContext();
                if (context == null) {
                    return 0;
                }
                count = appDatabase.movieDao().deleteId((int) ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            case TVSHOW_ID:
                context = getContext();
                if (context == null) {
                    return 0;
                }
                count = appDatabase.tvShowDao().deleteId((int) ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(@NotNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NotNull Uri uri, ContentValues values) {
        final Context context;
        final long id;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                context = getContext();
                if (context == null) {
                    return null;
                }
                id = appDatabase.movieDao().insertMovieProvider(MappingHelper.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case MOVIE_ID:
            case TVSHOW_ID:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            case TVSHOW:
                context = getContext();
                if (context == null) {
                    return null;
                }
                id = appDatabase.tvShowDao().insertTvShowProvider(MappingHelper.tvShowfromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
//        appDatabase = Room.databaseBuilder(Objects.requireNonNull(getContext()), DiscoverDatabase.class, DISCOVER_DATABASE).build();
        appDatabase = DiscoverDatabase.getDatabase(getContext());
        appDatabase.getOpenHelper().getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(@NotNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = appDatabase.movieDao().getAllMovieCursor();
                break;
            case MOVIE_ID:
                cursor = appDatabase.movieDao().selectMovie(Integer.parseInt(Objects.requireNonNull(uri.getLastPathSegment())));
                break;
            case TVSHOW:
                cursor = appDatabase.tvShowDao().getAllTvShowCursor();
                break;
            case TVSHOW_ID:
                cursor = appDatabase.tvShowDao().selectTvhow(Integer.parseInt(Objects.requireNonNull(uri.getLastPathSegment())));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return cursor;
//        final int code = sUriMatcher.match(uri);
//        if (code == MOVIE || code == MOVIE_ID) {
//            final Context context = getContext();
//            if (context == null) {
//                return null;
//            }
////            MenuDao menu = SampleDatabase.getInstance(context).menu();
//            final Cursor cursor;
//            if (code == MOVIE) {
//                cursor = appDatabase.movieDao().getAllMovieCursor();
//            } else {
//                cursor = appDatabase.movieDao().selectMovie((int) ContentUris.parseId(uri));
//            }
//            cursor.setNotificationUri(context.getContentResolver(), uri);
//            return cursor;
//        } else {
//            throw new IllegalArgumentException("Unknown URI: " + uri);
//        }
//        Objects.requireNonNull(cursor).setNotificationUri(Objects.requireNonNull(context).getContentResolver(), uri);
    }

    @Override
    public int update(@NotNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
