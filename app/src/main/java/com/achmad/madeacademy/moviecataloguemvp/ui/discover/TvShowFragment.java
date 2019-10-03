package com.achmad.madeacademy.moviecataloguemvp.ui.discover;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
public class TvShowFragment extends Fragment {

    RecyclerView rvTvShow;
    private ArrayList<Movie> tvShowList = new ArrayList<>();
    private ListDiscoverAdapter.OnFragmentInteractionListener onFragmentInteractionListener;
    private ListDiscoverAdapter mAdapter;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        tvShowList.addAll(new DiscoverData().getTvShow());
        mAdapter = new ListDiscoverAdapter(tvShowList, onFragmentInteractionListener);
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListDiscoverAdapter.OnFragmentInteractionListener) {
            onFragmentInteractionListener = (ListDiscoverAdapter.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }
}
