package com.dean.tryretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        name = findViewById(R.id.value_name);
        progressBar = findViewById(R.id.progressBar);

        SplashViewModel splashViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SplashViewModel.class);
        splashViewModel.getListReview().observe(this, consumerReviews -> {
            setReviewData(consumerReviews);
        });

        splashViewModel.isLoading().observe(this, this::showLoading);

       // new Handler().postDelayed(this::getNameSplash, 3000);
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setReviewData(List<NameItem> consumerReviews) {
        for (NameItem review : consumerReviews) {
            name.setText(review.getName());
        }
        Intent gotoMain = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(gotoMain);
    }
}