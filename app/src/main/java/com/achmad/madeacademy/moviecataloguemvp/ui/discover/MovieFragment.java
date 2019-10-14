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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Result;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.ListDiscoverAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {


    private RecyclerView rvMovies;

    private ListDiscoverAdapter.OnFragmentInteractionListener mListener;
    private ProgressBar progressBar;
    private DiscoverViewModel viewModel;
    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressBar = container.findViewById(R.id.progressBar);
        return  inflater.inflate(R.layout.fragment_movie, container, false);


//        DiscoverViewModel viewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(DiscoverViewModel.class);

//        DiscoverViewModel viewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(DiscoverViewModel.class);
        viewModel.getStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    rvMovies.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    rvMovies.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.setData().observe(this, new Observer<com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Movie>() {
            @Override
            public void onChanged(com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Movie listMovie) {
                showData(listMovie.getResults());
            }
        });



    }

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        return fragment;
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

    private void showData(List<Result> data) {
        rvMovies.setHasFixedSize(true);
        rvMovies.setAdapter(new ListDiscoverAdapter(data, mListener));
    }
}
