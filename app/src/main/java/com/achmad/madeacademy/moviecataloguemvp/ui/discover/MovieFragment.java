package com.achmad.madeacademy.moviecataloguemvp.ui.discover;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverContract;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.MovieAdapter;
import com.achmad.madeacademy.moviecataloguemvp.utils.CommonUtils;
import com.achmad.madeacademy.moviecataloguemvp.utils.MappingHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<Result> movieResult = new ArrayList<>();
    private MovieAdapter.OnFragmentInteractionListener mListener;
    private MovieAdapter mAdapter;
    private MovieViewModel movieViewModel;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private static final int MOVIE = 1;

    public MovieFragment() {
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
                return new CursorLoader(getActivity(), DiscoverContract.MOVIE_URI
                        , MOVIE_SUMMARY_PROJECTION, null, null, null);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            if (loader.getId() == MOVIE) {
                movieResult.addAll(MappingHelper.mapCursorToAlMovie(data));
                setRvMovies();
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            if (loader.getId() == MOVIE) {
                movieResult.clear();
                setRvMovies();
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvMovies = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_movies);
        CommonUtils.showLoading(getActivity());
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = preferences.getString("reply","popular_movies");
        if (sortOrder.equals("popular_movies")){
            initPopular();
        } else if (sortOrder.equals("top_rated")) {
            initTopRated();
        }else {
            if (savedInstanceState == null) {
                initDb();
                Log.d("savedInstance", "Ada");
            } else {
                Log.d("savedInstance", "Tidak Ada");
                ArrayList<Result> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
                if (list != null) {
                    mAdapter = new MovieAdapter(list, mListener);
                    rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvMovies.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
//            initDb();
        }
        movieViewModel.getCodeErro().observe(getViewLifecycleOwner(), integer -> {
                    switch (integer) {
                        case 401: {
                            CommonUtils.hideLoading();
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_401), Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 404: {
                            CommonUtils.hideLoading();
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_404), Toast.LENGTH_SHORT).show();
                            break;
                        }
                        default: {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        setRvMovies();
        mAdapter.notifyDataSetChanged();
    }


    private void initPopular() {
        movieViewModel.initPopular();
        movieViewModel.getMovieRepository().observe(getViewLifecycleOwner(), movieResponse -> {
            CommonUtils.hideLoading();
            try {
                movieResult.addAll(movieResponse.getResults());
            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
            }

            setRvMovies();
            mAdapter.notifyDataSetChanged();
        });
    }

    private void initTopRated() {
        movieViewModel.initTopRated();
        movieViewModel.getMovieRepository().observe(getViewLifecycleOwner(), movieResponse -> {
            CommonUtils.hideLoading();
            try {
                movieResult.addAll(movieResponse.getResults());
            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
            }

            setRvMovies();
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, (ArrayList<? extends Parcelable>) mAdapter.getListMovies());
    }


    public static MovieFragment newInstance() {
        return new MovieFragment();
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
//        movieViewModel.initDb();
//        movieViewModel.getMovieDb().observe(getViewLifecycleOwner(), movieResponse -> {
//            CommonUtils.hideLoading();
//            try {
//                movieResult.addAll(movieResponse);
//            } catch (Exception e) {
//                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
//            }
//            setRvMovies();
//            mAdapter.notifyDataSetChanged();
//        });
//        getSupportLoaderManager().initLoader(LOADER_CHEESES, null, mLoaderCallbacks);
        getActivity().getSupportLoaderManager().initLoader(MOVIE, null, mLoaderCallback);
    }

}
