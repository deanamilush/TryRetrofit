package com.dean.tryretrofit;

import com.dean.tryretrofit.api.BagianResponse;

import retrofit2.Call;
import retrofit2.http.*;

interface ApiService {
    @GET("kantin/bagian")
    Call<BagianResponse> getBagian();
}