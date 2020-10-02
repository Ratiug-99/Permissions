package com.ratiug.dev.permissions;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DBG | MainActivity";
    private static final int CODE_PERMISSION_WRITE_EXTERNAL = 1;
    Button btn1, btn2;
    EditText etText1, etText2;
    ImageView imageView;
    String path;
    Bitmap picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        checkWriteExternalPermission();

    }

    private void initializeViews() {
        btn1 = findViewById(R.id.btn_one);
        btn2 = findViewById(R.id.btn_two);
        imageView = findViewById(R.id.iv_iamge);
        etText1 = findViewById(R.id.et_text);
        etText2 = findViewById(R.id.et_text2);
        btn2.setEnabled(false);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImage();
            }
        });
    }

    private void checkWriteExternalPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onCreate: Permission write external storage granted!");
            } else {
                Log.d(TAG, "onCreate: Permission write external storage denied!");
                requestPermissionExternalWrite();
            }
        }
    }

    private void requestPermissionExternalWrite() {
        Log.d(TAG, "RequestPermissionExternalWrite");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_PERMISSION_WRITE_EXTERNAL);
    }


    private void downloadImage() {
        Log.d(TAG, "downloadImage");
        path = String.valueOf(etText1.getText());
        //    Log.d(TAG, "downloadImage: " + path.substring()); //todo check format (jpg/jpeg/png)
        //  getBitmapFromURL(src);
        String url = path;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Downloading...");
        request.setTitle("Picture");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "name-of-the-file.jpg");

// get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}