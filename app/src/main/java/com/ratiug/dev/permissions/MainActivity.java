package com.ratiug.dev.permissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap> {
    private static final String TAG = "DBG | MainActivity";
    private static final int CODE_PERMISSION_WRITE_EXTERNAL = 1;
    private static final int CODE_PERMISSION_INTERNET= 2;
    Button btn1, btn2;
    EditText etText1, etText2;
    ImageView image;
    Loader mLoader;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        checkWriteExternalPermission();
        checkInternetPermission();


    }

    private void initializeViews() {
        btn1 = findViewById(R.id.btn_one);
        btn2 = findViewById(R.id.btn_two);
        image = findViewById(R.id.iv_iamge);
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

    private void checkInternetPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onCreate: Permission internet granted!");
            } else {
                Log.d(TAG, "onCreate: Permission internet denied!");
                requestPermissionExternalWrite();
            }
        }
    }

    private void requestPermissionInternet() {
        Log.d(TAG, "RequestPermissionInternet");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, CODE_PERMISSION_INTERNET);
    }




    private void downloadImage() {
        Log.d(TAG, "downloadImage");
        path = String.valueOf(etText1.getText());
        getSupportLoaderManager().restartLoader(1, new Bundle(), this).forceLoad();
        mLoader = getSupportLoaderManager().getLoader(1);
        //  getBitmapFromURL(src);
    }

    @NonNull
    @Override
    public Loader<Bitmap> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader");
        return new DownloadLoader(this, path);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bitmap> loader, Bitmap data) {
        Log.d(TAG, "onLoadFinished " + data.toString());
        image.setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Bitmap> loader) {
        Log.d(TAG, "onLoaderReset");
    }
}