package com.example.bixapp.users;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserInterface {

    @GET("users")
    Call<ResponseBody> getAll(@Query("_format") String format,
                              @Query("page") Integer page,
                              @Query("access-token") String token,
                              @Query("last_name") String lastName
    );
}
