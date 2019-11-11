package com.achmad.selflearning.mydiscoverconsumer.data.remote;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.selflearning.mydiscoverconsumer.data.local.DiscoverDatabase;
import com.achmad.selflearning.mydiscoverconsumer.data.local.dao.MovieDao;
import com.achmad.selflearning.mydiscoverconsumer.data.local.dao.TvShowDao;
import com.achmad.selflearning.mydiscoverconsumer.data.remote.model.movie.Result;

import java.util.List;

public class NetworkRepository {
    private static NetworkRepository networkRepository;
    private MovieDao movieDao;
    private TvShowDao tvShowDao;
    private LiveData<List<Result>> mAllMovie;
    private LiveData<List<com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result>> mAllTvShow;
    private MutableLiveData<Integer> codeError = new MutableLiveData<>();

    public NetworkRepository(Application application) {
        DiscoverDatabase db = DiscoverDatabase.getDatabase(application);
        movieDao = db.movieDao();
        tvShowDao = db.tvShowDao();
    }

    public MutableLiveData<Integer> getErrorCode() {
        return codeError;
    }

    public LiveData<List<Result>> getAllMovie() {
        mAllMovie = movieDao.getAllMovie();
        return mAllMovie;
    }

    public LiveData<List<com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result>> getAllTvShow() {
        mAllTvShow = tvShowDao.getAllTvShow();
        return mAllTvShow;
    }

    public LiveData<List<com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result>> getTvShowDb(int id) {
        mAllTvShow = tvShowDao.getTvShow(id);
        return mAllTvShow;
    }

    public LiveData<List<Result>> getMovieDb(int id) {
        mAllMovie = movieDao.getMovie(id);
        return mAllMovie;
    }

    public void insertMovie(Result movie) {
        new insertAsyncTaskMovie(movieDao).execute(movie);
    }

    public void insertTvShow(com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result tvshow) {
        new insertAsyncTaskTvShow(tvShowDao).execute(tvshow);
    }

    public void deleteDbMovie(Result movie) {
        new deleteAsyncTaskMovie(movieDao).execute(movie);
    }

    public void deleteDbTvShow(com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result tvShow) {
        new deleteAsyncTaskTvShow(tvShowDao).execute(tvShow);
    }

    private static class insertAsyncTaskMovie extends AsyncTask<Result, Void, Void> {
        private MovieDao movieAsyncDao;

        insertAsyncTaskMovie(MovieDao dao) {
            movieAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            movieAsyncDao.insertMovie(results[0]);
            return null;
        }
    }

    private static class deleteAsyncTaskMovie extends AsyncTask<Result, Void, Void> {
        private MovieDao movieDao;

        deleteAsyncTaskMovie(MovieDao dao) {
            movieDao = dao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            movieDao.delete(results[0]);
            return null;
        }
    }

    private static class deleteAsyncTaskTvShow extends AsyncTask<com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result, Void, Void> {
        private TvShowDao tvShowDao;

        deleteAsyncTaskTvShow(TvShowDao dao) {
            tvShowDao = dao;
        }

        @Override
        protected Void doInBackground(com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result... results) {
            tvShowDao.delete(results[0]);
            return null;
        }
    }

    private static class insertAsyncTaskTvShow extends AsyncTask<com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result, Void, Void> {
        private TvShowDao movieAsyncDao;

        insertAsyncTaskTvShow(TvShowDao dao) {
            movieAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result... results) {
            movieAsyncDao.insertMovie(results[0]);
            return null;
        }
    }

}

