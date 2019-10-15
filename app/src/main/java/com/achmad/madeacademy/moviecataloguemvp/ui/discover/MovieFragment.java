package com.achmad.madeacademy.moviecataloguemvp.ui.discover;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Movie;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Result;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.ListDiscoverAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView rvMovies;
    ArrayList<Result> movieResult = new ArrayList<>();
    private ListDiscoverAdapter.OnFragmentInteractionListener mListener;
    private ProgressBar progressBar;
    ListDiscoverAdapter mAdapter;
    DiscoverViewModel discoverViewModel;
    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = Objects.requireNonNull(getActivity()).findViewById(R.id.progressBar);
        rvMovies = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_movies);

//        DiscoverViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DiscoverViewModel.class);
        discoverViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        discoverViewModel.init();
        discoverViewModel.getMovieRepository().observe(this,movieResponse->{
            movieResult.addAll(movieResponse.getResults());
            mAdapter.notifyDataSetChanged();
        });
        setRvMovies();
//
//        viewModel.getStatus().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                if (aBoolean) {
//                    rvMovies.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.VISIBLE);
//                } else {
//                    rvMovies.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        });
//        viewModel.setData().observe(this, new Observer<com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Movie>() {
//            @Override
//            public void onChanged(com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Movie listMovie) {
//                showData(listMovie.getResults());
//            }
//        });
    }


    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListDiscoverAdapter.OnFragmentInteractionListener) {
            mListener = (ListDiscoverAdapter.OnFragmentInteractionListener) context;
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

//    private void showData(List<Result> data) {
//
//        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rvMovies.setHasFixedSize(true);
//        rvMovies.setAdapter(new ListDiscoverAdapter(data, mListener));
//    }

    private void setRvMovies(){
        if (mAdapter ==null){
            mAdapter = new ListDiscoverAdapter(movieResult,mListener);
            rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvMovies.setAdapter(mAdapter);
        }
    }
}
