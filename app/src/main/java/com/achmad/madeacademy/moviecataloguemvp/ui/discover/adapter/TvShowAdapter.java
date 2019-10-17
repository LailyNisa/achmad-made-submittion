package com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.tvshow.Result;
import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private final List<Result> mValues;
    private final OnFragmentInteractionListener mListener;

    public TvShowAdapter(List<Result> mValues, OnFragmentInteractionListener mListener) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Result tvShow = mValues.get(position);

        holder.tvTitle.setText(tvShow.getName());
        final View.OnClickListener onClickListener = view -> mListener.onFragmentInteraction(mValues.get(holder.getAdapterPosition()));
        holder.tvTitle.setOnClickListener(onClickListener);
        holder.tvRelease.setText(tvShow.getFirstAirDate());
        holder.dntProgress.setProgress((Math.round(tvShow.getVoteAverage()*100))/10);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185"+tvShow.getPosterPath())
                .into(holder.imgPhoto);
        holder.imgPhoto.setOnClickListener(onClickListener);
        holder.tvOverview.setText(tvShow.getOverview());
        holder.tvMore.setOnClickListener(onClickListener);
        if (tvShow.getOriginalName()!=null){
            holder.progressBar.setVisibility(View.GONE);
        }else {
            holder.progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (mValues == null) ? 0 : mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRelease, tvOverview, tvMore;
        ImageView imgPhoto;
        DonutProgress dntProgress;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRelease = itemView.findViewById(R.id.tv_date_release);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            imgPhoto = itemView.findViewById(R.id.img_person);
            dntProgress = itemView.findViewById(R.id.donute_progress);
            tvMore = itemView.findViewById(R.id.tv_more_info);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    public interface OnFragmentInteractionListener{
        void onFragmentInteraction(Result tvShow);
    }
}
