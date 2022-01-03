package com.dean.tryretrofit;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dean.tryretrofit.api.nama.NamaResponse;
import com.dean.tryretrofit.api.nama.NameItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashViewModel extends ViewModel {

    private final MutableLiveData<List<NameItem>> _listReview = new MutableLiveData<>();
    public LiveData<List<NameItem>> getListReview() {
        return _listReview;
    }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    private static final String TAG = "SplashViewModel";

    public SplashViewModel() {
        new Handler().postDelayed(this::getNameSplash, 3000);
    }

    private void getNameSplash() {
        _isLoading.setValue(true);
        Call<NamaResponse> client = ApiConfig.getApiService().getNamaUser();
        client.enqueue(new Callback<NamaResponse>() {
            @Override
            public void onResponse(@NotNull Call<NamaResponse> call, @NotNull Response<NamaResponse> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        _listReview.setValue(response.body().getData());
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: " + response.body().getMessage());
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<NamaResponse> call, @NotNull Throwable t) {
                _isLoading.setValue(false);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
