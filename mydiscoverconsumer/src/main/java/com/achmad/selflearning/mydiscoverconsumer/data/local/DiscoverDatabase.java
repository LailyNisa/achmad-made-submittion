package com.achmad.selflearning.mydiscoverconsumer.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.achmad.selflearning.mydiscoverconsumer.data.local.dao.MovieDao;
import com.achmad.selflearning.mydiscoverconsumer.data.local.dao.TvShowDao;
import com.achmad.selflearning.mydiscoverconsumer.data.remote.model.movie.Result;

import static com.achmad.selflearning.mydiscoverconsumer.data.local.DiscoverContract.DISCOVER_DATABASE;

@Database(entities = {Result.class, com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result.class}, version = 1)
public abstract class DiscoverDatabase extends RoomDatabase {
    private static volatile DiscoverDatabase INSTANCE;

    public static DiscoverDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DiscoverDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DiscoverDatabase.class, DISCOVER_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract MovieDao movieDao();

    public abstract TvShowDao tvShowDao();
}
