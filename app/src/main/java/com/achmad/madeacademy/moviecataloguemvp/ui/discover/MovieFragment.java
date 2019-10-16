package com.achmad.madeacademy.moviecataloguemvp.ui.discover;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.movie.Result;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView rvMovies;
    private ArrayList<Result> movieResult = new ArrayList<>();
    private MovieAdapter.OnFragmentInteractionListener mListener;
    private ProgressBar progressBar;
    private MovieAdapter mAdapter;
    private MovieViewModel movieViewModel;

    public MovieFragment() {
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
//        progressBar = Objects.requireNonNull(getActivity()).findViewById(R.id.progressBar);
        rvMovies = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_movies);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.init();
        movieViewModel.getMovieRepository().observe(this, movieResponse -> {
            movieResult.addAll(movieResponse.getResults());
            if (movieResponse.getPage() != 0) {
                rvMovies.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
//                progressBar.showAndPlay();

            } else {
                rvMovies.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
//                progressBar.hide();
            }
            mAdapter.notifyDataSetChanged();
        });
        setRvMovies();
    }


    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onAttach(Context context) {
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
}
