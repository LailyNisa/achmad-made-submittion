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
import com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result;
import com.achmad.madesubmittion.favorite.ui.discover.adapter.TvShowAdapter;
import com.achmad.madesubmittion.favorite.utils.MappingHelper;

import java.util.ArrayList;

import static com.achmad.madesubmittion.favorite.data.local.DiscoverContract.TVSHOW_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private static final int TVSHOW = 2;
    private RecyclerView rvTvShow;
    private ArrayList<Result> tvShowList = new ArrayList<>();
    private TvShowAdapter.OnFragmentInteractionListener mListener;
    private TvShowAdapter mAdapter;

    private LoaderManager.LoaderCallbacks<Cursor> mTvShowLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        final String[] TVSHOW_SUMMARY_PROJECTION = new String[]{
                DiscoverContract.TvShowColumns.ORIGINAL_NAME,
                DiscoverContract.TvShowColumns.NAME,
                DiscoverContract.TvShowColumns.POPULARITY,
                DiscoverContract.TvShowColumns.VOTE_COUNT,
                DiscoverContract.TvShowColumns.FIRSTAIRDATE,
                DiscoverContract.TvShowColumns.BACKDROPPATH,
                DiscoverContract.TvShowColumns.ORIGINAL_LANGUAGE,
                DiscoverContract.TvShowColumns.ID,
                DiscoverContract.TvShowColumns.VOTE_AVERAGE,
                DiscoverContract.TvShowColumns.OVERVIEW,
                DiscoverContract.TvShowColumns.POSTERPATH,
        };

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            if (id == TVSHOW) {
                return new CursorLoader(getActivity(), TVSHOW_URI
                        , TVSHOW_SUMMARY_PROJECTION, null, null, null);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            if (loader.getId() == TVSHOW) {
                tvShowList.addAll(MappingHelper.mapCursorToAlTvShow(data));
                setRvMovies();
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            if (loader.getId() == TVSHOW) {
                setRvMovies();
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    public TvShowFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            rvTvShow = (RecyclerView) view;
            rvTvShow.setHasFixedSize(true);
            rvTvShow.setLayoutManager(new LinearLayoutManager(context));
            rvTvShow.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            initDb();
        } else {
            ArrayList<Result> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                mAdapter = new TvShowAdapter(list, mListener);
                rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvTvShow.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
        setRvMovies();
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, (ArrayList<Result>) mAdapter.getListTvShow());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TvShowAdapter.OnFragmentInteractionListener) {
            mListener = (TvShowAdapter.OnFragmentInteractionListener) context;
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
            mAdapter = new TvShowAdapter(tvShowList, mListener);
            rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvTvShow.setAdapter(mAdapter);
        }
    }

    private void initDb() {
//        tvShowList.addAll(new ArrayList<>());
//
//        setRvMovies();
//        mAdapter.notifyDataSetChanged();
        getActivity().getSupportLoaderManager().initLoader(TVSHOW, null, mTvShowLoaderCallback);
    }
}
