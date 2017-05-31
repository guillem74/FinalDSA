package edu.upc.dsa.finaldsa;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Guillem on 31/05/2017.
 */

interface Service {

    @GET("/users/{name}/followers")
    Call<List<Follower>> getList(@Path("name") String name);
}
