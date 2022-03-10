package com.dean.tryretrofit;

import com.dean.tryretrofit.api.BagianResponse;
import com.dean.tryretrofit.api.ImageResponse;
import com.dean.tryretrofit.api.login.LoginResponse;
import com.dean.tryretrofit.api.nama.NamaResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    @Multipart
    @POST("imgUpload")
    Call<ImageResponse> uploadPhotoBase64(
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    // @Headers({"Authorization: token 12345"})
    @POST("login")
    Call<LoginResponse> getUserLogin(
            @Field("email") String email,
            @Field("password") String password
    );
}