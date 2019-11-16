package com.achmad.madesubmittion.favorite.ui.detail;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.achmad.madesubmittion.favorite.R;
import com.achmad.madesubmittion.favorite.data.local.DiscoverContract;
import com.achmad.madesubmittion.favorite.data.remote.model.movie.Result;
import com.achmad.madesubmittion.favorite.ui.discover.DiscoverActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.achmad.madesubmittion.favorite.data.local.DiscoverContract.MOVIE_URI;
import static com.achmad.madesubmittion.favorite.data.local.DiscoverContract.TVSHOW_URI;
import static com.achmad.madesubmittion.favorite.utils.Const.BACKDROP_PATH;
import static com.achmad.madesubmittion.favorite.utils.Const.POSTER_PATH;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_OBJECT = "object_extra_movie";
    public static final String EXTRA_OBJECT_MOVIE = "object_extra_movie_instance";
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
    com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result tvshow;
    LiveData<Result> mAllMovie;
    private Uri uriWithId;
    private DiscoverDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BaseTheme);
        setContentView(R.layout.activity_detail_constraint);
        movie = new Result();
        tvshow = new com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result();
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

    private Result getMovie() {
        return movie;
    }

    private void setMovie(Result movie) {
        this.movie = movie;
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
        uriWithId = Uri.parse(MOVIE_URI + "/" + movie.getId());
        imgButton.setOnClickListener(view -> {
            mViewModel.onFavoriteClicked();
            if (!mViewModel.isFavorite()) {
                imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                getContentResolver().delete(uriWithId, null, null);
            } else if (movie.getId() != 0) {
                imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                ContentValues values = new ContentValues();
                values.put(DiscoverContract.MovieColumns.POPULARITY, movie.getPopularity());
                values.put(DiscoverContract.MovieColumns.VOTE_COUNT, movie.getVoteCount());
                int isVideo = movie.isVideo() ? 1 : 0;
                values.put(DiscoverContract.MovieColumns.VIDEO, isVideo);
                values.put(DiscoverContract.MovieColumns.POSTER_PATH, movie.getPosterPath());
                values.put(DiscoverContract.MovieColumns.ID, movie.getId());
                int isAdult = movie.isAdult() ? 1 : 0;
                values.put(DiscoverContract.MovieColumns.ADULT, isAdult);
                values.put(DiscoverContract.MovieColumns.BACKDROP_PATH, movie.getBackdropPath());
                values.put(DiscoverContract.MovieColumns.ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
                values.put(DiscoverContract.MovieColumns.ORIGINAL_TITLE, movie.getOriginalTitle());
                values.put(DiscoverContract.MovieColumns.TITLE, movie.getTitle());
                values.put(DiscoverContract.MovieColumns.VOTE_AVERAGE, movie.getVoteAverage());
                values.put(DiscoverContract.MovieColumns.OVERVIEW, movie.getOverview());
                values.put(DiscoverContract.MovieColumns.RELEASE_DATE, movie.getReleaseDate());
                getContentResolver().insert(MOVIE_URI, values);
            } else {
                Intent backToParent = new Intent(this, DiscoverActivity.class);
                startActivity(backToParent);
                finish();
            }

        });
        mViewModel.initDbMovieId(movie.getId());
        mViewModel.getMovieRepository().observe(this, results -> {
                    if (results == null) {
            imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
            mViewModel.setFavorite(false);
        } else {
            imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
            mViewModel.setFavorite(true);
        }
                }
        );

    }

    private void bindTvShow(com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result tvShow) {
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
        uriWithId = Uri.parse(TVSHOW_URI + "/" + tvShow.getId());
        imgButton.setOnClickListener(view -> {
            mViewModel.onFavoriteClicked();
            if (!mViewModel.isFavorite()) {
                imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                getContentResolver().delete(uriWithId, null, null);
            } else {
                imgButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                ContentValues values = new ContentValues();
                values.put(DiscoverContract.TvShowColumns.ORIGINAL_NAME, tvShow.getOriginalName());
                values.put(DiscoverContract.TvShowColumns.NAME, tvShow.getName());
                values.put(DiscoverContract.TvShowColumns.POPULARITY, tvShow.getPopularity());
                values.put(DiscoverContract.TvShowColumns.VOTE_COUNT, tvShow.getVoteCount());
                values.put(DiscoverContract.TvShowColumns.FIRSTAIRDATE, tvShow.getFirstAirDate());
                values.put(DiscoverContract.TvShowColumns.BACKDROPPATH, tvShow.getBackdropPath());
                values.put(DiscoverContract.TvShowColumns.ORIGINAL_LANGUAGE, tvShow.getOriginalLanguage());
                values.put(DiscoverContract.TvShowColumns.ID, tvShow.getId());
                values.put(DiscoverContract.TvShowColumns.VOTE_AVERAGE, tvShow.getVoteAverage());
                values.put(DiscoverContract.TvShowColumns.OVERVIEW, tvShow.getOverview());
                values.put(DiscoverContract.TvShowColumns.POSTERPATH, tvShow.getPosterPath());
                getContentResolver().insert(TVSHOW_URI, values);
            }
        });

        mViewModel.initDbTvShowId(tvShow.getId());
        mViewModel.getTvShowRepository().observe(this, results -> {
                    if (results == null) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailMovieActivity.this, DiscoverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
