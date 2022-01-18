package com.dean.tryretrofit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.DirectAction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dean.tryretrofit.api.ImageResponse;
import com.dean.tryretrofit.api.login.LoginResponse;
import com.dean.tryretrofit.database.User;
import com.dean.tryretrofit.databinding.ActivityUploadBinding;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    private final int CAMERA = 2;
    private Bitmap FixBitmap;
    ActivityUploadBinding uploadBinding;
    private ByteArrayOutputStream byteArrayOutputStream ;
    Uri mediaPath;
    String filePath = "";
    private static final String TAG = "UploadActivity";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadBinding = ActivityUploadBinding.inflate(getLayoutInflater());
        setContentView(uploadBinding.getRoot());


        uploadBinding.buttonPhoto.setOnClickListener(this);
        uploadBinding.buttonUpload.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(UploadActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                    5);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            uploadBinding.imageView.setImageBitmap(FixBitmap);
            uploadBinding.buttonUpload.setVisibility(View.VISIBLE);
            uploadBinding.buttonPhoto.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_photo){
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }

        if(view.getId() == R.id.button_upload){
            postImage();
        }
    }

    private void postImage() {
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(filePath);

        // Parsing any Media type file
//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        MultipartBody.Part body2 = MultipartBody.Part.createFormData("avatar", file.getName(), new FileRequestBody(file, extension));

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("newimage", file.getName(), requestBody);

        Call <ImageResponse> call = ApiConfig.getApiPatrol().uploadPhotoBase64(parts);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("success")){

                            Toast.makeText(UploadActivity.this, "BERHASIIIIIIIL", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UploadActivity.this);
                            alertDialogBuilder.setTitle("Error");
                            alertDialogBuilder
                                    .setMessage(response.body().getStatus())
                                    .setCancelable(false)
                                    .setPositiveButton("Okay", (dialog, arg1) -> {
                                        dialog.cancel();
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: " + response.body().getStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

    private File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(extStorageDirectory, "temp.png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "temp.png");

        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
            else {
                showSnackbarMessage("Tidak dapat menggunakan kamera..Harap izinkan penggunaan kamera");
            }
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(uploadBinding.rlSelfie, message, Snackbar.LENGTH_SHORT).show();
    }
}