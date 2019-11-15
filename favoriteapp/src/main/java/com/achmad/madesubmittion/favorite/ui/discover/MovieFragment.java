package com.achmad.madesubmittion.favorite.ui.discover;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madesubmittion.favorite.R;
import com.achmad.madesubmittion.favorite.data.local.DiscoverContract;
import com.achmad.madesubmittion.favorite.data.remote.model.movie.Result;
import com.achmad.madesubmittion.favorite.ui.discover.adapter.MovieAdapter;
import com.achmad.madesubmittion.favorite.utils.CommonUtils;
import com.achmad.madesubmittion.favorite.utils.MappingHelper;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.achmad.madesubmittion.favorite.data.local.DiscoverContract.MOVIE_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private static final int MOVIE = 1;
    private RecyclerView rvMovies;
    private ArrayList<Result> movieResult = new ArrayList<>();
    private MovieAdapter.OnFragmentInteractionListener mListener;
    private MovieAdapter mAdapter;
    private MovieViewModel movieViewModel;
    private MutableLiveData<List<Result>> mutableLiveData = new MutableLiveData<>();
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        final String[] MOVIE_SUMMARY_PROJECTION = new String[]{
                DiscoverContract.MovieColumns.ID,
                DiscoverContract.MovieColumns.ADULT,
                DiscoverContract.MovieColumns.BACKDROP_PATH,
                DiscoverContract.MovieColumns.ORIGINAL_LANGUAGE,
                DiscoverContract.MovieColumns.ORIGINAL_TITLE,
                DiscoverContract.MovieColumns.OVERVIEW,
                DiscoverContract.MovieColumns.POPULARITY,
                DiscoverContract.MovieColumns.POSTER_PATH,
                DiscoverContract.MovieColumns.RELEASE_DATE,
                DiscoverContract.MovieColumns.TITLE,
                DiscoverContract.MovieColumns.VIDEO,
                DiscoverContract.MovieColumns.VOTE_AVERAGE,
                DiscoverContract.MovieColumns.VOTE_COUNT,
        };

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            if (id == MOVIE) {
                return new CursorLoader(getActivity(), MOVIE_URI
                        , MOVIE_SUMMARY_PROJECTION, null, null, null);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            CommonUtils.hideLoading();
            if (loader.getId() == MOVIE) {
//                MutableLiveData<List<Result>> mutableLiveData = new MutableLiveData<>();
                if (isResumed()) {
//                    mutableLiveData.setValue(MappingHelper.mapCursorToAlMovie(data));
//                    movieViewModel.setMutableLiveDataMovie(getMovieBack().getValue());
                    Log.d("lifecycle", "resume sblm isi " + movieResult.size());
                    if (movieResult.size() == 0) {
                        movieResult.addAll(MappingHelper.mapCursorToAlMovie(data));
                        Log.d("lifecycle", "resume setelah isi" + movieResult.size());
                        setRvMovies();
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            if (loader.getId() == MOVIE) {
                movieViewModel.setMutableLiveDataMovie(null);
            }
        }
    };

    public MovieFragment() {
    }

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            rvMovies = (RecyclerView) view;
            rvMovies.setHasFixedSize(true);
            rvMovies.setLayoutManager(new LinearLayoutManager(context));
            rvMovies.setAdapter(mAdapter);
        }
        return view;
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvMovies, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvMovies = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_movies);
        CommonUtils.showLoading(getActivity());
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this.getActivity());
        getActivity().getContentResolver().registerContentObserver(MOVIE_URI, true, myObserver);
        if (savedInstanceState == null) {
            initDb();
        } else {
            CommonUtils.hideLoading();
            ArrayList<Result> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                mAdapter = new MovieAdapter(list, mListener);
                rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvMovies.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
        setRvMovies();
        mAdapter.notifyDataSetChanged();
    }

    public LiveData<List<Result>> getMovieBack() {
        return mutableLiveData;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, (ArrayList<Result>) mAdapter.getListMovies());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("lifecycle", "resume " + movieResult.size());
    }

    //
    @Override
    public void onPause() {
        super.onPause();
//        getActivity().finish();
    }


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof MovieAdapter.OnFragmentInteractionListener) {
            mListener = (MovieAdapter.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setRvMovies() {
        if (mAdapter == null) {
            mAdapter = new MovieAdapter(movieResult, mListener);
            rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvMovies.setAdapter(mAdapter);
        }
    }

    private void initDb() {
        getActivity().getSupportLoaderManager().initLoader(MOVIE, null, mLoaderCallback);
    }

    public class DataObserver extends ContentObserver {
        private LoaderManager.LoaderCallbacks mCallback;
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
//            getActivity().getSupportLoaderManager().initLoader(MOVIE, null, mLoaderCallback);
//            getActivity().finish();
//            Log.d("mboh","mboh");
        }
    }
}
