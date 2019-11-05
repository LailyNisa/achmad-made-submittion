package com.achmad.madeacademy.moviecataloguemvp.ui.detail;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

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
    TextView tvTitle, tvRelease, tvOverView, tvCast, tvTitleBar;
    DonutProgress dntProgressDetail;
    ImageView imgPoster, imgBackdrop;
    CircleImageView imgCast;
    Toolbar toolbar;
    AppBarLayout appbar;
    ImageButton imgButton;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Result movie;
    com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvshow;
    private DiscoverDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BaseTheme);
        setContentView(R.layout.activity_detail_constraint);
        movie = new Result();
        tvshow = new com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result();
        movie = getIntent().getParcelableExtra(EXTRA_OBJECT);
        tvshow = getIntent().getParcelableExtra(EXTRA_OBJECT_TVSHOW);
        mViewModel = new ViewModelProvider(this).get(DiscoverDetailViewModel.class);
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
        imgButton = findViewById(R.id.favorite_button_detail);
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
        imgButton.setOnClickListener(view -> {
            mViewModel.onFavoriteClicked();
            if (!mViewModel.isFavorite()) {
                imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                mViewModel.deleteMovie(movie);
            } else {
                imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                mViewModel.insertMovie(movie);
            }

        });
        mViewModel.initDbMovieId(movie.getId());
        mViewModel.getMovieRepository().observe(this, results -> {
                    if (results.isEmpty()) {
                        Toast.makeText(this, "Lagi kosong", Toast.LENGTH_SHORT).show();
                        imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                        mViewModel.setFavorite(false);
                    } else {
                        Toast.makeText(this, "Ada isinya", Toast.LENGTH_SHORT).show();
                        imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                        mViewModel.setFavorite(true);
                    }
                }
        );

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
        imgButton.setOnClickListener(view -> {
            mViewModel.onFavoriteClicked();
            if (!mViewModel.isFavorite()) {
                imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                mViewModel.deleteTvshow(tvShow);
            } else {
                imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                mViewModel.insertTvShow(tvShow);
            }

        });
        mViewModel.initTvShow(tvShow.getId());
        mViewModel.getTvShowRepository().observe(this, results -> {
                    if (results.isEmpty()) {
                        imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                        mViewModel.setFavorite(false);
                    } else {
                        imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                        mViewModel.setFavorite(true);
                    }
                }
        );
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
