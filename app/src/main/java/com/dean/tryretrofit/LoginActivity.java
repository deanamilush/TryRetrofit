package com.dean.tryretrofit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dean.tryretrofit.api.login.LoginResponse;
import com.google.android.material.card.MaterialCardView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email, password;
    private Button btnLogin;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        btnLogin.setEnabled(false);
        String loginUser = email.getText().toString().trim();
        String loginPass = password.getText().toString().trim();
        String FIELD_REQUIRED = "Tidak boleh kosong";
        if(TextUtils.isEmpty(loginUser)){
            btnLogin.setEnabled(true);
            email.setError(FIELD_REQUIRED);
            email.requestFocus();
        } else if(TextUtils.isEmpty(loginPass)){
            btnLogin.setEnabled(true);
            password.setError(FIELD_REQUIRED);
            password.requestFocus();
        } else {
            postLogin();
        }
    }

    private void postLogin() {
        String loginUser = email.getText().toString().trim();
        String loginPass = password.getText().toString().trim();
        Call<LoginResponse> client = ApiConfig.getApiService().getLogin(loginUser, loginPass);
        client.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("success")){

                        Toast.makeText(LoginActivity.this, "BERHASIIIIIIIL", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                            alertDialogBuilder.setTitle("Error");
                            alertDialogBuilder
                                    .setMessage(response.body().getMessage())
                                    .setCancelable(false)
                                    .setPositiveButton("Okay", (dialog, arg1) -> {
                                        btnLogin.setEnabled(true);
                                        dialog.cancel();
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: " + response.body().getMessage());
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}