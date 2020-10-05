package com.ratiug.dev.permissions;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DBG | MainActivity";
    private static final int CODE_PERMISSION_WRITE_EXTERNAL = 1;
    Button btn1, btn2;
    EditText etText1;
    ImageView imageView;
    String path;
    String lastDownloadPath;
    Bitmap picture;
    String filename;
    BroadcastReceiver broadcastReceiverFinishDownload;
    ///


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        checkWriteExternalPermission();

        broadcastReceiverFinishDownload = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                btn2.setEnabled(true);
            }
        };
        IntentFilter filterFinishDownload = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(broadcastReceiverFinishDownload, filterFinishDownload);
    }


    private void initializeViews() {
        btn1 = findViewById(R.id.btn_one);
        btn2 = findViewById(R.id.btn_two);
        imageView = findViewById(R.id.iv_iamge);
        etText1 = findViewById(R.id.et_text);
        btn2.setEnabled(false);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImage();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage();
            }
        });
    }

    private void setImage() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
        if (file.exists()) {
            picture = BitmapFactory.decodeFile(String.valueOf(file));
            imageView.setImageBitmap(picture);
        }
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
        path = String.valueOf(etText1.getText());
        if (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png") || path.endsWith(".bmp")) {
            String url = path;
            filename = path.substring(path.lastIndexOf("/") + 1);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Downloading...");
            request.setTitle(filename);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

// get download service and enqueue file
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
            lastDownloadPath = "Internal shared storage/Download/" + "android-1.jpg"; //+ filename
        } else {
            Toast.makeText(this, "Incorrect link", Toast.LENGTH_SHORT).show();
        }
    }
}