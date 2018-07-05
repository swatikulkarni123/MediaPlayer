package com.example.harshalchaudhari.myapplication2.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Songs implements Parcelable {
    private String song;
    private String url;
    private String artists;
    private String cover_image;
/*
    public Songs(String song, String url, String artists, String cover_image) {
        this.song = song;
        this.url = url;
        this.artists = artists;
        this.cover_image = cover_image;
    }*/

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    protected Songs(Parcel in) {
        song = in.readString();
        url = in.readString();
        artists = in.readString();
        cover_image = in.readString();
    }

    public static final Creator<Songs> CREATOR = new Creator<Songs>() {
        @Override
        public Songs createFromParcel(Parcel in) {
            return new Songs(in);
        }

        @Override
        public Songs[] newArray(int size) {
            return new Songs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(song);
        parcel.writeString(url);
        parcel.writeString(artists);
        parcel.writeString(cover_image);
    }
}

