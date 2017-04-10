package com.softwareleague.app.sladmin.data.api;


import com.softwareleague.app.sladmin.data.api.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SoftwareLeagueApiService {

    @GET("Login")
    Call<LoginResponse> getLogin(
            @Query("username") String username,
            @Query("password") String password
    );

}
