package com.achmad.selflearning.mydiscoverconsumer.ui.discover;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.selflearning.mydiscoverconsumer.R;
import com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow.Result;
import com.achmad.selflearning.mydiscoverconsumer.ui.discover.adapter.TvShowAdapter;
import com.achmad.selflearning.mydiscoverconsumer.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private RecyclerView rvTvShow;
    private ArrayList<Result> tvShowList = new ArrayList<>();
    private TvShowAdapter.OnFragmentInteractionListener mListener;
    private TvShowAdapter mAdapter;
    private TvShowViewModel tvShowViewModel;

    public TvShowFragment() {
        // Required empty public constructor
    }

    public static TvShowFragment newInstance() {
        return new TvShowFragment();
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
        tvShowViewModel = new ViewModelProvider(this).get(TvShowViewModel.class);
        initDb();
    }

    private void initDb() {
        tvShowViewModel.initDb();
        tvShowViewModel.getTvShowDb().observe(getViewLifecycleOwner(), movieResponse -> {
            CommonUtils.hideLoading();
            try {
                tvShowList.addAll(movieResponse);
            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
            }
            setRvMovies();
            mAdapter.notifyDataSetChanged();
        });
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
}
