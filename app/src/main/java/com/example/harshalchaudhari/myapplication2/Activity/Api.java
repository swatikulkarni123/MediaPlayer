package com.example.harshalchaudhari.myapplication2.Activity;

import com.example.harshalchaudhari.myapplication2.Models.Songs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api  {
    String BASE_URL="http://starlord.hackerearth.com/";

    @GET("studio")
    Call<List<Songs>> getSongs();

}
