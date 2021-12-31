package com.dean.tryretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.dean.tryretrofit.api.nama.NamaResponse;
import com.dean.tryretrofit.api.nama.NameItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private TextView name;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        name = findViewById(R.id.value_name);

        new Handler().postDelayed(this::getNameSplash, 3000);
    }

    private void getNameSplash() {
        Call<NamaResponse> client = ApiConfig.getApiService().getNamaUser();
        client.enqueue(new Callback<NamaResponse>() {
            @Override
            public void onResponse(@NotNull Call<NamaResponse> call, @NotNull Response<NamaResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        setReviewData(response.body().getData());
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: " + response.body().getMessage());
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<NamaResponse> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void setReviewData(List<NameItem> consumerReviews) {
        for (NameItem review : consumerReviews) {
            name.setText(review.getName());
        }
        Intent gotoMain = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(gotoMain);
    }

    private void getCurrentVersion() {
        Intent gotoMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(gotoMain);
    }
}