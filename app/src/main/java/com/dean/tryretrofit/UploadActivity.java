package com.dean.tryretrofit;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.DirectAction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.tryretrofit.api.ImageResponse;
import com.dean.tryretrofit.api.login.LoginResponse;
import com.dean.tryretrofit.database.BitmapUtils;
import com.dean.tryretrofit.database.User;
import com.dean.tryretrofit.databinding.ActivityUploadBinding;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityUploadBinding uploadBinding;
    private static final String TAG = "UploadActivity";

    private String mTempPhotoPath;
    private TextView takeDate;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String FILE_PROVIDER_AUTHORITY = "com.dean.tryretrofit.fileprovider";

    private AutoCompleteTextView noTrx;
    private final ArrayList<String> arrTrx = new ArrayList<String>();
    private ByteArrayOutputStream byteArrayOutputStream;
    Bitmap fixBitmap;
    private String ConvertImage;

    /*final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == REQUEST_IMAGE_CAPTURE && result.getResultCode() == RESULT_OK) {
                    int selectedValue = result.getData().getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0);
                    tvResult.setText(String.format("Hasil : %s", selectedValue));
                }
            });*/

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadBinding = ActivityUploadBinding.inflate(getLayoutInflater());
        setContentView(uploadBinding.getRoot());

        byteArrayOutputStream = new ByteArrayOutputStream();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat dfPlus = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        SimpleDateFormat dfPls = new SimpleDateFormat("hh:mm", Locale.ENGLISH);

        String formatted = dfPls.format(c);

        // Adding 10 mins using Date constructor.
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
        Date afterToleransi = new Date(timeInSecs + (2 * 60 * 1000));
        String formattedDate = dfPlus.format(afterToleransi);
        uploadBinding.take1.setText(formattedDate);
        uploadBinding.take.setText(formatted);

        uploadBinding.buttonPhoto.setOnClickListener(this);
        uploadBinding.buttonUpload.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(UploadActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                    5);
        }

    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +"_image.jpg");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,50, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void SendImage() {
        fixBitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(70000);
        RequestParams params = new RequestParams();
        params.put("image", ConvertImage);
        String url = "http://36.91.208.116/dev-kantin-primary/public/api/imgUpload";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(UploadActivity.this, "berhasil...........", Toast.LENGTH_SHORT).show();

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
                        errorMessage =  statusCode + " : " + error.getMessage();
                        break;
                }

                String mError;
                if (errorMessage.length() >= 22){
                    mError = errorMessage.substring(0, 22 );
                }else {
                    mError = errorMessage.substring(0, errorMessage.length() - 1 );
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UploadActivity.this);
                alertDialogBuilder.setTitle("Error Hubungi Administrator..!");
                alertDialogBuilder
                        .setMessage(mError)
                        .setCancelable(false)
                        .setPositiveButton("Coba Lagi", (dialog, arg1) -> {
                            dialog.cancel();
                            uploadBinding.buttonUpload.setEnabled(true);
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage();
            uploadBinding.buttonUpload.setVisibility(View.VISIBLE);
            uploadBinding.buttonPhoto.setEnabled(false);
        } else {

            // Otherwise, delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath);
        }
    }

    private void processAndSetImage() {
        // Resample the saved image to fit the ImageView
        fixBitmap = BitmapUtils.resamplePic(this, mTempPhotoPath);
//        fixBitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
        // Set the new bitmap to the ImageView
        uploadBinding.imageView.setImageBitmap(fixBitmap);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_photo){

            // Create the capture image intent
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the temporary File where the photo should go
                File photoFile = null;
                try {
                    photoFile = BitmapUtils.createTempImageFile(this);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {

                    // Get the path of the temporary file
                    mTempPhotoPath = photoFile.getAbsolutePath();

                    // Get the content URI for the image file
                    Uri photoURI = FileProvider.getUriForFile(this,
                            FILE_PROVIDER_AUTHORITY,
                            photoFile);

                    // Add the URI so the camera can store the image
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    // Launch the camera activity
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }

        if(view.getId() == R.id.button_upload){
            isInternetOn();
        }
    }

    public final void isInternetOn() {

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            // connected to the internet
//            postImage(fixBitmap);
            SendImage();

        } else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UploadActivity.this);
            alertDialogBuilder.setTitle("Foto Gagal di Unggah");
            alertDialogBuilder
                    .setMessage("Cek Koneksi Internet Anda")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, arg1) -> {
                        uploadBinding.buttonUpload.setEnabled(true);
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(descriptionString,
                okhttp3.MultipartBody.FORM);
    }

    private void postImage(Bitmap uploadImage) {
        File file = createTempFile(uploadImage);
//        File file = new File(mTempPhotoPath);
        RequestBody fbody = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), fbody);

        Call <ImageResponse> call = ApiConfig.getApiPatrol().uploadPhotoBase64(parts);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImageResponse> call, @NonNull Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UploadActivity.this, "BERHASIIIIIIIL", Toast.LENGTH_SHORT).show();
                    /*if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("success")){


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
                    }*/
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