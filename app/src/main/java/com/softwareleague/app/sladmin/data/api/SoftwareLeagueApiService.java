package com.softwareleague.app.sladmin.data.api;


import com.softwareleague.app.sladmin.data.api.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SoftwareLeagueApiService {

    @GET("campeonato.json")
    Call<LoginResponse> getLogin(@Query("username") String username, @Query("password") String password);

}
