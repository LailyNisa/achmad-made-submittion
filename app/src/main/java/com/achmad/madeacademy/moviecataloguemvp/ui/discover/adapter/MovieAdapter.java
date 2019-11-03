package com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;
import com.achmad.madeacademy.moviecataloguemvp.utils.BaseViewHolder;
import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.List;

import static com.achmad.madeacademy.moviecataloguemvp.utils.Const.POSTER_PATH;
import static com.achmad.madeacademy.moviecataloguemvp.utils.Const.VIEW_TYPE_EMPTY;
import static com.achmad.madeacademy.moviecataloguemvp.utils.Const.VIEW_TYPE_NORMAL;

public class MovieAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final List<Result> mValues;

    private final OnFragmentInteractionListener mListener;

    public MovieAdapter(List<Result> mValues, OnFragmentInteractionListener mListener) {
        this.mValues = mValues;
        this.mListener = mListener;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.fragment_movie_constraint, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_empty, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mValues != null && mValues.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (mValues != null && mValues.size() > 0) {
            return mValues.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {
        TextView tvTitle, tvRelease, tvOverview, tvMore;
        ImageView imgPhoto;
        DonutProgress dntProgress;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRelease = itemView.findViewById(R.id.tv_date_release);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            imgPhoto = itemView.findViewById(R.id.img_person);
            dntProgress = itemView.findViewById(R.id.donute_progress);
            tvMore = itemView.findViewById(R.id.tv_more_info);
            progressBar = itemView.findViewById(R.id.progressbar);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            final Result movie = mValues.get(position);

            tvTitle.setText(movie.getTitle());
            final View.OnClickListener onClickListener = view -> mListener.onFragmentInteraction(mValues.get(getAdapterPosition()));
            tvTitle.setOnClickListener(onClickListener);
            tvRelease.setText(movie.getReleaseDate());
            dntProgress.setProgress((Math.round(movie.getVoteAverage() * 100)) / 10);
            Glide.with(itemView.getContext())
                    .load(POSTER_PATH + movie.getPosterPath())
                    .into(imgPhoto);
            imgPhoto.setOnClickListener(onClickListener);
            tvOverview.setText(movie.getOverview());
            tvMore.setOnClickListener(onClickListener);
            if (movie.getTitle() != null) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void clear() {

        }
    }

    public class EmptyViewHolder extends BaseViewHolder {
        Button buttonRetry;

        EmptyViewHolder(View itemView) {
            super(itemView);
            buttonRetry = itemView.findViewById(R.id.buttonRetry);
        }

        @Override
        protected void clear() {

        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Result movie);

    }


}
