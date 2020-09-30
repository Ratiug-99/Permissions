package com.ratiug.dev.permissions;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2;
    EditText etText1, etText2;
    ImageView image;

    private static final String TAG = "DBG | MainActivity";
    private static final int CODE_PERMISSION_WRITE_EXTERNAL = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn_one);
        btn2 = findViewById(R.id.btn_two);
        etText1 = findViewById(R.id.et_text);
        etText2 = findViewById(R.id.et_text2);
        image = findViewById(R.id.iv_iamge);

        btn2.setEnabled(false);

        CheckWriteExternalPermission();

    }

    private void CheckWriteExternalPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "onCreate: Permission granted! +");
            }
            else {
                Log.d(TAG, "onCreate: Permission denied! -");
                RequestPermissionExternalWrite();
            }

        }
    }

    private void RequestPermissionExternalWrite() {
        Log.d(TAG, "RequestPermissionExternalWrite");
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE }, CODE_PERMISSION_WRITE_EXTERNAL);
    }
}