package edu.upc.dsa.finaldsa;


import android.widget.ImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface Service {

    @GET("/users/{name}/followers")
    Call<List<Follower>> getList(@Path("name") String name);

    @GET("/users/{name}")
    Call<Follower> getFollower(@Path("name") String name);

    @GET("/users/{name}/following")
    Call<List<Follower>> getListrepos(@Path("name") String name);
}