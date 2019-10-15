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
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Result;
import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.List;

public class ListDiscoverAdapter extends RecyclerView.Adapter<ListDiscoverAdapter.ViewHolder> {
    RecyclerView rvMovies;
    //    private ArrayList<Movie> movieList = new ArrayList<>();
    private ListDiscoverAdapter mAdapter;
    private final List<Result> mValues;
    private final OnFragmentInteractionListener mListener;

    public ListDiscoverAdapter(List<Result> mValues, OnFragmentInteractionListener mListener) {
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
        final Result movie = mValues.get(position);

        holder.tvTitle.setText(movie.getTitle());
        final View.OnClickListener onClickListener = view -> mListener.onFragmentInteraction(mValues.get(holder.getAdapterPosition()));
        holder.tvTitle.setOnClickListener(onClickListener);
        holder.tvRelease.setText(movie.getReleaseDate());
        holder.dntProgress.setProgress((Math.round(movie.getVoteAverage()*100))/10);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185"+movie.getPosterPath())
                .into(holder.imgPhoto);
        holder.imgPhoto.setOnClickListener(onClickListener);
        holder.tvOverview.setText(movie.getOverview());
        holder.tvMore.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return (mValues == null) ? 0 : mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRelease, tvOverview, tvMore;
        ImageView imgPhoto;
        DonutProgress dntProgress;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRelease = itemView.findViewById(R.id.tv_date_release);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            imgPhoto = itemView.findViewById(R.id.img_person);
            dntProgress = itemView.findViewById(R.id.donute_progress);
            tvMore = itemView.findViewById(R.id.tv_more_info);
        }

//        void bind(Result movie){
//            tvTitle.setText(movie.getTitle());
//            tvRelease.setText(movie.getReleaseDate());
//            tvOverview.setText(movie.getOverview());
//            Glide.with(itemView)
//                    .load("https://image.tmdb.org/t/p/w185"+movie.getBackdropPath())
//                    .into(imgPhoto);
//            dntProgress.setProgress((int) movie.getVoteAverage());
//        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Result movie);
    }
}
