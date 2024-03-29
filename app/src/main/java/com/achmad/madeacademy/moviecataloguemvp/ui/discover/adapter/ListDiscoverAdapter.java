package com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.Movie;
import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;

public class ListDiscoverAdapter extends RecyclerView.Adapter<ListDiscoverAdapter.ViewHolder> {
    RecyclerView rvMovies;
    //    private ArrayList<Movie> movieList = new ArrayList<>();
    private ListDiscoverAdapter mAdapter;
    private final ArrayList<Movie> mValues;
    private final OnFragmentInteractionListener mListener;

    public ListDiscoverAdapter(ArrayList<Movie> mValues, OnFragmentInteractionListener mListener) {
        this.mValues = mValues;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_constraint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Movie movie = mValues.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(mValues.get(holder.getAdapterPosition()));
            }
        });
        holder.tvRelease.setText(movie.getRelease());
        holder.dntProgress.setProgress(Integer.parseInt(movie.getUser_score()));
        Glide.with(holder.itemView.getContext())
                .load(movie.getImg_poster())
                .into(holder.imgPhoto);
        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(mValues.get(holder.getAdapterPosition()));
            }
        });
        holder.tvOverview.setText(movie.getOverview());
        holder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(mValues.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mValues == null) ? 0 : mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRelease, tvOverview, tvMore;
        ImageView imgPhoto;
        DonutProgress dntProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRelease = itemView.findViewById(R.id.tv_date_release);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            imgPhoto = itemView.findViewById(R.id.img_person);
            dntProgress = itemView.findViewById(R.id.donute_progress);
            tvMore = itemView.findViewById(R.id.tv_more_info);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Movie movie);
    }
}
