package com.ratiug.dev.permissions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2;
    EditText etText1, etText2;
    ImageView image;


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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        }

    }
}