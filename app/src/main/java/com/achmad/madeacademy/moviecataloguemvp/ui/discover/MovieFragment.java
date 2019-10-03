package com.achmad.madeacademy.moviecataloguemvp.ui.discover;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.Movie;
import com.achmad.madeacademy.moviecataloguemvp.data.source.local.DiscoverData;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.ListDiscoverAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {


    RecyclerView rvMovies;
    private ArrayList<Movie> movieList = new ArrayList<>();
    private ListDiscoverAdapter movieAdapter;
    private ListDiscoverAdapter.OnFragmentInteractionListener mListener;

    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        movieList.addAll(new DiscoverData().getMovie());
        movieAdapter = new ListDiscoverAdapter(movieList, mListener);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            rvMovies = (RecyclerView) view;
            rvMovies.setHasFixedSize(true);
            rvMovies.setLayoutManager(new LinearLayoutManager(context));
            rvMovies.setAdapter(movieAdapter);
        }
        return view;
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
}
