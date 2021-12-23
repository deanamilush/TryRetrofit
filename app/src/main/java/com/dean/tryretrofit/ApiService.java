package com.dean.tryretrofit;

import com.dean.tryretrofit.api.BagianResponse;
import com.dean.tryretrofit.api.nama.NamaResponse;

import retrofit2.Call;
import retrofit2.http.*;

interface ApiService {
    @GET("kantin/bagian")
    Call<BagianResponse> getBagian();

    @GET("kantin/setuser")
    Call<NamaResponse> getNamaUser();
}