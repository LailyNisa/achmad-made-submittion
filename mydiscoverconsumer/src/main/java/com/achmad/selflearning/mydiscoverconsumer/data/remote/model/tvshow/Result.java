package com.achmad.selflearning.mydiscoverconsumer.data.remote.model.tvshow;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static com.achmad.selflearning.mydiscoverconsumer.data.local.DiscoverContract.TVSHOW_TABLE;

@Entity(tableName = TVSHOW_TABLE)
public class Result implements Parcelable {

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @Ignore
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private float popularity;
    @Ignore
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int id;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    @Expose
    private float voteAverage;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    @Expose
    private String overview;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    public Result() {
    }

    protected Result(Parcel in) {
        this.originalName = in.readString();
        this.genreIds = new ArrayList<>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.name = in.readString();
        this.popularity = in.readFloat();
        this.originCountry = in.createStringArrayList();
        this.voteCount = in.readInt();
        this.firstAirDate = in.readString();
        this.backdropPath = in.readString();
        this.originalLanguage = in.readString();
        this.id = in.readInt();
        this.voteAverage = in.readFloat();
        this.overview = in.readString();
        this.posterPath = in.readString();
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalName);
        dest.writeList(this.genreIds);
        dest.writeString(this.name);
        dest.writeFloat(this.popularity);
        dest.writeStringList(this.originCountry);
        dest.writeInt(this.voteCount);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.backdropPath);
        dest.writeString(this.originalLanguage);
        dest.writeInt(this.id);
        dest.writeFloat(this.voteAverage);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
    }
}