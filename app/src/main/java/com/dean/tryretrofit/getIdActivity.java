package com.dean.tryretrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;
import com.dean.tryretrofit.databinding.ActivityGetIdBinding;

public class getIdActivity extends AppCompatActivity {

    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.dean.tryretrofit.databinding.ActivityGetIdBinding getIdBinding = ActivityGetIdBinding.inflate(getLayoutInflater());
        setContentView(getIdBinding.getRoot());

        @SuppressLint("HardwareIds") String idApp = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        @SuppressLint("HardwareIds") String idSerial = android.os.Build.SERIAL;

        getIdBinding.valueName.setText(idApp);

        getIdBinding.buttonCopy.setOnClickListener(view -> {
            myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            myClip = ClipData.newPlainText("text", idApp);
            myClipboard.setPrimaryClip(myClip);

            Toast.makeText(getApplicationContext(), "Android ID Copied",Toast.LENGTH_SHORT).show();
        });
    }
}