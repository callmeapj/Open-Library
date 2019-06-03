package com.example.aprajeyakhouri.finalapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Basicsearchservice {
    @GET("/search.json")
    Call<Basicsearch>getsearchdata(@Query("q") String title);
}
