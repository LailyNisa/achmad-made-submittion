package com.achmad.madeacademy.moviecataloguemvp.data.remote;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverDatabase;
import com.achmad.madeacademy.moviecataloguemvp.data.local.dao.MovieDao;
import com.achmad.madeacademy.moviecataloguemvp.data.local.dao.TvShowDao;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Movie;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.TvShow;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkRepository {
    private static NetworkRepository networkRepository;
    private MovieDao movieDao;
    private TvShowDao tvShowDao;
    private LiveData<List<Result>> mAllMovie;
    private MutableLiveData<List<Result>> mutableLiveDataMovie = new MutableLiveData<>();
    private LiveData<List<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result>> mAllTvShow;


//    public static NetworkRepository getInstance() {
//        if (networkRepository == null) {
//            networkRepository = new NetworkRepository();
//        }
//        return networkRepository;
//    }

    private ApiService movieApi;

    public NetworkRepository(Application application) {
        movieApi = NetworkConfig.getInstance();
        DiscoverDatabase db = DiscoverDatabase.getDatabase(application);
        movieDao = db.movieDao();
        tvShowDao = db.tvShowDao();
    }

    public MutableLiveData<Movie> getMovie(String sort) {
        final MutableLiveData<Movie> movieData = new MutableLiveData<>();
        movieApi.getMovie(sort).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }


    public MutableLiveData<TvShow> getTvShow(String sort) {
        final MutableLiveData<TvShow> tvShowMutableLiveData = new MutableLiveData<>();
        movieApi.getTvShow(sort).enqueue(new Callback<TvShow>() {

            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                if (response.isSuccessful()) {
                    tvShowMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<TvShow> call, @NotNull Throwable t) {
                tvShowMutableLiveData.setValue(null);
            }
        });
        return tvShowMutableLiveData;
    }

    public LiveData<List<Result>> getAllMovie() {
        mAllMovie = movieDao.getAllMovie();
        return mAllMovie;
    }

    public LiveData<List<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result>> getAllTvShow() {
        mAllTvShow = tvShowDao.getAllTvShow();
        return mAllTvShow;
    }

    public LiveData<List<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result>> getTvShowDb(int id) {
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

    public void insertTvShow(com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvshow) {
        new insertAsyncTaskTvShow(tvShowDao).execute(tvshow);
    }

    public void deleteDbMovie(Result movie) {
        new deleteAsyncTaskMovie(movieDao).execute(movie);
    }

    public void deleteDbTvShow(com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvShow) {
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

    private static class deleteAsyncTaskTvShow extends AsyncTask<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result, Void, Void> {
        private TvShowDao tvShowDao;

        deleteAsyncTaskTvShow(TvShowDao dao) {
            tvShowDao = dao;
        }

        @Override
        protected Void doInBackground(com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result... results) {
            tvShowDao.delete(results[0]);
            return null;
        }
    }

    private static class insertAsyncTaskTvShow extends AsyncTask<com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result, Void, Void> {
        private TvShowDao movieAsyncDao;

        insertAsyncTaskTvShow(TvShowDao dao) {
            movieAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result... results) {
            movieAsyncDao.insertMovie(results[0]);
            return null;
        }
    }

}

