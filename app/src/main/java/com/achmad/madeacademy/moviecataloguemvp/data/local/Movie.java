package com.achmad.madeacademy.moviecataloguemvp.data.local;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String title;
    private String release;
    private String user_score;
    private String img_poster;
    private String overview;
    private String featured_crew;
    private String img_featured_crew;
    private String img_Backdrop;

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getUser_score() {
        return user_score;
    }

    public void setUser_score(String user_score) {
        this.user_score = user_score;
    }

    public String getImg_poster() {
        return img_poster;
    }

    public void setImg_poster(String img_poster) {
        this.img_poster = img_poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFeatured_crew() {
        return featured_crew;
    }

    public void setFeatured_crew(String featured_crew) {
        this.featured_crew = featured_crew;
    }

    public String getImg_featured_crew() {
        return img_featured_crew;
    }

    public void setImg_featured_crew(String img_featured_crew) {
        this.img_featured_crew = img_featured_crew;
    }

    public String getImg_Backdrop() {
        return img_Backdrop;
    }

    public void setImg_Backdrop(String img_Backdrop) {
        this.img_Backdrop = img_Backdrop;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeString(this.user_score);
        dest.writeString(this.img_poster);
        dest.writeString(this.overview);
        dest.writeString(this.featured_crew);
        dest.writeString(this.img_featured_crew);
        dest.writeString(this.img_Backdrop);

    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.release = in.readString();
        this.user_score = in.readString();
        this.img_poster = in.readString();
        this.overview = in.readString();
        this.featured_crew = in.readString();
        this.img_featured_crew = in.readString();
        this.img_Backdrop = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
