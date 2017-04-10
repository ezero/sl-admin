package com.softwareleague.app.sladmin.data.api;

import com.softwareleague.app.sladmin.data.api.model.Affiliate;
import com.softwareleague.app.sladmin.data.api.model.LoginBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Headers;


public interface SoftwareLeagueApi {

    public static final String BASE_URL = "http://10.0.0.39:8080/api/";

    @GET("Users")
    @Headers("Accept:application/json")
    Call<Affiliate> Users(@Body LoginBody loginBody);

}
