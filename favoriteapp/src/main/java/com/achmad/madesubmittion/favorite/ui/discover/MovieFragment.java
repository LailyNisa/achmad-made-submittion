package com.achmad.madesubmittion.favorite.ui.discover;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
    //    private ArrayList<Result> movieResult = new ArrayList<>();
    private MovieAdapter.OnFragmentInteractionListener mListener;
    private MovieAdapter mAdapter;
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
                if (isResumed()) {
                    if (movieResult.size() == 0) {
                        movieResult.addAll(MappingHelper.mapCursorToAlMovie(data));
                        setRvMovies();
                        mAdapter.notifyDataSetChanged();
                    }
                }
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvMovies = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_movies);
        CommonUtils.showLoading(getActivity());
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, (ArrayList<Result>) mAdapter.getListMovies());
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
}
