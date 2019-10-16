package com.achmad.madeacademy.moviecataloguemvp.ui.discover;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.tvshow.Result;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.TvShowAdapter;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<Result> tvShowList = new ArrayList<>();
    private TvShowAdapter.OnFragmentInteractionListener mListener;
    private ProgressBar progressBar;
    private TvShowAdapter mAdapter;
    private TvShowViewModel discoverViewModel;

    public TvShowFragment() {
        // Required empty public constructor
    }

    public static TvShowFragment newInstance() {
        TvShowFragment tvShowFragment = new TvShowFragment();
        return tvShowFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie, container, false);
        progressBar = v.findViewById(R.id.progressBar);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvMovies = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_movies);
        discoverViewModel = new ViewModelProvider(this).get(TvShowViewModel.class);
        discoverViewModel.init();
        discoverViewModel.getTvShowRepository().observe(this, movieResponse -> {
            tvShowList.addAll(movieResponse.getResults());
            if (movieResponse.getPage() != 0) {
                rvMovies.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
                rvMovies.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
            mAdapter.notifyDataSetChanged();
        });
        setRvMovies();
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
        mListener =null;
    }

    private void setRvMovies() {
        if (mAdapter == null) {
            mAdapter = new TvShowAdapter(tvShowList,mListener);
            rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvMovies.setAdapter(mAdapter);
        }
    }
}
