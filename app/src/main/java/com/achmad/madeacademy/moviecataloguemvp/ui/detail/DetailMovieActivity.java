package com.achmad.madeacademy.moviecataloguemvp.ui.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.achmad.madeacademy.moviecataloguemvp.utils.Const.BACKDROP_PATH;
import static com.achmad.madeacademy.moviecataloguemvp.utils.Const.POSTER_PATH;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_OBJECT = "object_extra_movie";
    public static final String EXTRA_OBJECT_TVSHOW = "object_extra_tvshow";
    public static final String EXTRA_TITLE = "object_title";
    TextView tvTitle, tvRelease, tvOverView, tvCast, tvTitleBar;
    DonutProgress dntProgressDetail;
    ImageView imgPoster, imgBackdrop;
    CircleImageView imgCast;
    Toolbar toolbar;
    AppBarLayout appbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Result movie;
    com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BaseTheme);
        setContentView(R.layout.activity_detail_constraint);
        movie = new Result();
        tvshow = new com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result();
        movie = getIntent().getParcelableExtra(EXTRA_OBJECT);
        tvshow = getIntent().getParcelableExtra(EXTRA_OBJECT_TVSHOW);
        initObject();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (movie != null) {
            bindMovie(movie);
            handleCollapsedToolbarTitle();
        } else {
            bindTvShow(tvshow);
            handleCollapsedToolbarTitle();
        }
    }

    private void initObject() {
        imgBackdrop = findViewById(R.id.image_movie_backdrop);
        tvTitle = findViewById(R.id.tv_title_detail_constraint);
        tvRelease = findViewById(R.id.tv_release_detail_constraint);
        dntProgressDetail = findViewById(R.id.donute_progress_constraint);
        tvOverView = findViewById(R.id.tv_overview_detail_constraint);
        imgPoster = findViewById(R.id.img_poster_constraint);
        imgCast = findViewById(R.id.img_cast_constraint);
        tvCast = findViewById(R.id.tv_cast_constraint);
        toolbar = findViewById(R.id.toolbar_constraint_detail);
        appbar = findViewById(R.id.appbar_constraint_detail);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        tvTitleBar = findViewById(R.id.toolbar_title);
    }

    private void bindMovie(Result movie) {
        tvTitle.setText(movie.getTitle());
        tvRelease.setText(movie.getReleaseDate());
        dntProgressDetail.setProgress((Math.round(movie.getVoteAverage() * 100)) / 10);
        tvOverView.setText(movie.getOverview());
        Glide.with(this)
                .load(POSTER_PATH + movie.getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(imgPoster);
        Glide.with(this)
                .load(BACKDROP_PATH + movie.getBackdropPath())
                .into(imgBackdrop);
    }

    private void bindTvShow(com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvShow) {
        tvTitle.setText(tvShow.getName());
        tvRelease.setText(tvShow.getFirstAirDate());
        dntProgressDetail.setProgress((Math.round(tvShow.getVoteAverage() * 100)) / 10);
        tvOverView.setText(tvShow.getOverview());
        Glide.with(this)
                .load(POSTER_PATH + tvShow.getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(imgPoster);
        Glide.with(this)
                .load(BACKDROP_PATH + tvShow.getBackdropPath())
                .into(imgBackdrop);
    }

    private void handleCollapsedToolbarTitle() {
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    if (movie != null) {
                        tvTitleBar.setText(movie.getTitle());
                    } else {
                        tvTitleBar.setText(tvshow.getName());
                    }

                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

    }
}
