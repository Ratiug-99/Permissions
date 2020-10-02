package com.ratiug.dev.permissions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadLoader extends AsyncTaskLoader<Bitmap> {
    String mSrc;
    public DownloadLoader(@NonNull Context context, String src) {

        super(context);
        mSrc = src;
        Log.d("DBG", "DownloadLoader: ");
    }

    @Nullable
    @Override
    public Bitmap loadInBackground() {
        Log.d("DBG", "loadInBackground: ");
       return getBitmapFromURL(mSrc);
    }

    private Bitmap getBitmapFromURL(String path) {
        Log.d("DBG", "getBitmapFromURL: Start");
        InputStream in = null;
        Bitmap bmp = null;

        int responseCode = -1;
        try {
            URL url = new URL(path);//"http://192.xx.xx.xx/mypath/img1.jpg
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.connect();
            responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //download
                in = con.getInputStream();
                bmp = BitmapFactory.decodeStream(in);
                in.close();
                Log.d("DBG", "getBitmapFromURL: Return bmp");
                return bmp;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("DBG", "getBitmapFromURL: Return NULL");
            return null;
        }
        return null;
    }
}
