package com.dean.tryretrofit;

import com.dean.tryretrofit.api.BagianResponse;
import com.dean.tryretrofit.api.login.LoginResponse;
import com.dean.tryretrofit.api.nama.NamaResponse;

import retrofit2.Call;
import retrofit2.http.*;

interface ApiService {
    @GET("kantin/bagian")
    Call<BagianResponse> getBagian();

    @GET("kantin/setuser")
    Call<NamaResponse> getNamaUser();

    @FormUrlEncoded
   // @Headers({"Authorization: token 12345"})
    @POST("kantin/login")
    Call<LoginResponse> getLogin(
            @Field("email") String email,
            @Field("password") String password
    );
}