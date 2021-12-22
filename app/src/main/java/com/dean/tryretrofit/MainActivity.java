package com.dean.tryretrofit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dean.tryretrofit.api.BagianResponse;
import com.dean.tryretrofit.api.DataItem;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BagianAdapter bagianAdapter;
    private final ArrayList<DataUser> list = new ArrayList<>();
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.fragment_days);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);

       // getMenuDays();
        findRestaurant();
    }

    private void findRestaurant() {
        Call<BagianResponse> client = ApiConfig.getApiService().getBagian();
        client.enqueue(new Callback<BagianResponse>() {
            @Override
            public void onResponse(@NotNull Call<BagianResponse> call, @NotNull Response<BagianResponse> response) {
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
            public void onFailure(@NotNull Call<BagianResponse> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void setReviewData(List<DataItem> consumerReviews) {
        ArrayList<DataItem> listReview = new ArrayList<>();
        /*review.setKode(review.getKode());
            review.setDeskripsiBagian(review.getDeskripsiBagian());*/
        //            listReview.add(review.getKode() + "\n- " + review.getDeskripsiBagian());
        listReview.addAll(consumerReviews);
        BagianAdapter adapter = new BagianAdapter(listReview);
        recyclerView.setAdapter(adapter);
    }

    private void getMenuDays() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://developer.gsg.co.id/Kantin/public/api/kantin/bagian";
        client.get(url, new AsyncHttpResponseHandler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray pData = responseObject.getJSONArray("data");

                    for (int i = 0; i < pData.length(); i++) {
                        DataUser pUser = new DataUser();
                        JSONObject jsonObject = pData.getJSONObject(i);
                        pUser.kode = jsonObject.getString("kode");
                        pUser.bagian = jsonObject.getString("deskripsi_bagian");
                        list.add(pUser);
                        bagianAdapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbidden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;
                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                }

                String mError;
                if (errorMessage.length() >= 22) {
                    mError = errorMessage.substring(0, 22);
                } else {
                    mError = errorMessage.substring(0, errorMessage.length() - 1);
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("ERROR");
                alertDialogBuilder
                        .setMessage(mError)
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, arg1) -> {
                            dialog.cancel();
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}